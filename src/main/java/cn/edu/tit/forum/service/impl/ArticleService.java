package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.AchieveDTO;
import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.enums.ArticleDelStatusEnum;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.mapper.ArticleExtMapper;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.mapper.ThumbUpMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.*;
import cn.edu.tit.forum.service.IArticleService;
import cn.edu.tit.forum.utils.DateUtil;
import cn.edu.tit.forum.utils.KeyUtil;
import cn.edu.tit.forum.utils.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/9
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThumbUpMapper thumbUpMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Integer> hashOps;

    // 首页 文章列表
    @Override
    public PageInfo<ArticleDTO> queryList(Integer page, Integer size, String tag, String search) {
        PageHelper.startPage(page, size);
        List<Article> articles = articleExtMapper.selectBySearchAndTag(tag, search);

        PageInfo<Article> articlePoPageInfo = new PageInfo<>(articles);
        PageInfo<ArticleDTO> articleVoPageInfo = PageUtil.PageInfo2PageInfoVo(articlePoPageInfo);

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO();
            User user = userMapper.selectByPrimaryKey(article.getAuthorId());
            articleDTO.setUser(user);
            // Spring提供的，快速封装结果集
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setGmtCreate(DateUtil.format(article.getGmtCreate()));
            articleDTO.setGmtModified(DateUtil.format(article.getGmtModified()));

            String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
            String hk_viewCount = KeyUtil.getHashArticleViewCount(article.getId());
            if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
                articleDTO.setViewCount(hashOps.get(k_viewCount, hk_viewCount));
            }
            String k_likeCount = KeyUtil.ARTICLE_LIKE_COUNT;
            String hk_likeCount = KeyUtil.getHashArticleLikeCount(article.getId());
            if (hashOps.hasKey(k_likeCount, hk_viewCount)) {
                articleDTO.setLikeCount(hashOps.get(k_likeCount, hk_likeCount));
            }

            String content = article.getContent();
            if (content.length() > 50) {
                articleDTO.setContent(content.substring(0, 70) + "...");
            }

            articleDTOS.add(articleDTO);
        }
        for (ArticleDTO articleDTO : articleDTOS) {
            articleVoPageInfo.getList().add(articleDTO);
        }
        return articleVoPageInfo;
    }

    // 根据问题ID查询 dto
    @Override
    public ArticleDTO getById(Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);

        // 处理 question/{id} id手动输入，但查找不到的异常
        if (article == null || article.getDelFlag().equals(ArticleDelStatusEnum.DELETE.getStatus())) {
            throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);
        }

        User user = userMapper.selectByPrimaryKey(article.getAuthorId());

        // Redis查询数量
        String k_count = KeyUtil.ARTICLE_LIKE_COUNT;
        String hk_status = KeyUtil.getHashArticleLikeCount(id);
        if (hashOps.hasKey(k_count, hk_status)) {// 有key,从缓存中查询
            Integer likeCount = hashOps.get(k_count, hk_status);
            article.setLikeCount(likeCount);
        }
        String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
        String hk_viewCount = KeyUtil.getHashArticleViewCount(id);
        if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
            article.setViewCount(hashOps.get(k_viewCount, hk_viewCount));
        }

        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        articleDTO.setGmtModified(DateUtil.format(article.getGmtModified()));
        articleDTO.setUser(user);
        return articleDTO;
    }

    // 处理发布页 和 编辑页两种发布语境
    @Override
    public void createOrUpdate(Article article) {
        String tag = article.getTag();
        char[] chars = tag.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == ',' && chars[i + 1] == ',') {
                chars[i] = '0';
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == '0') continue;
            sb.append(c);
        }
        article.setTag(sb.toString());
        if (article.getId() == null) {
            article.setGmtCreate(System.currentTimeMillis());
            article.setGmtModified(article.getGmtCreate());
            article.setViewCount(0);
            article.setLikeCount(0);
            article.setCommentCount(0);
            // 第一次创建
            articleMapper.insert(article);
        } else {
            // 更新
            Article updateArticle = new Article();

            updateArticle.setGmtModified(System.currentTimeMillis());
            updateArticle.setTitle(article.getTitle());
            updateArticle.setContent(article.getContent());
            updateArticle.setTag(article.getTag());

            ArticleExample updateArticleExample = new ArticleExample();
            updateArticleExample.createCriteria().andIdEqualTo(article.getId());
            int updated = articleMapper.updateByExampleSelective(updateArticle, updateArticleExample);

            // 处理当一个页面在修改，另起一个窗口将问题删除的情况 提交不成功
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);
            }
        }
    }

    // 增加阅读数， 根据问题ID
    public void incView(Long id) {
        // Redis
        String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
        String hk_viewCount = KeyUtil.getHashArticleViewCount(id);
        if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
            hashOps.increment(k_viewCount, hk_viewCount, 1L);
        } else {
            Article article = articleMapper.selectByPrimaryKey(id);
            hashOps.put(k_viewCount, hk_viewCount, article.getViewCount());
        }
        /*Article article = new Article();
        article.setId(id);
        article.setViewCount(1);
        articleExtMapper.incView(article);*/
    }

    @Override
    public PageInfo<ArticleDTO> listByUser(int page, int size, Long userId) {
        PageHelper.startPage(page, size);
        List<Article> articles = articleExtMapper.findByUser(userId);

        PageInfo<Article> articlePoPageInfo = new PageInfo<>(articles);
        PageInfo<ArticleDTO> articleVoPageInfo = PageUtil.PageInfo2PageInfoVo(articlePoPageInfo);

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO();
            User user = userMapper.selectByPrimaryKey(article.getAuthorId());
            articleDTO.setUser(user);
            articleDTO.setGmtCreate(DateUtil.format(article.getGmtCreate()));
            articleDTO.setGmtModified(DateUtil.format(article.getGmtModified()));
            // Spring提供的，快速封装结果集
            BeanUtils.copyProperties(article, articleDTO);

            String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
            String hk_viewCount = KeyUtil.getHashArticleViewCount(article.getId());
            if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
                articleDTO.setViewCount(hashOps.get(k_viewCount, hk_viewCount));
            }
            String k_count = KeyUtil.ARTICLE_LIKE_COUNT;
            String hk_count = KeyUtil.getHashArticleLikeCount(article.getId());
            if (hashOps.hasKey(k_count, hk_count)) {
                articleDTO.setLikeCount(hashOps.get(k_count, hk_count));
            }

            String content = article.getContent();
            if (content.length() > 50) {
                articleDTO.setContent(content.substring(0, 70) + "...");
            }

            articleDTOS.add(articleDTO);
        }
        for (ArticleDTO articleDTO : articleDTOS) {
            articleVoPageInfo.getList().add(articleDTO);
        }
        return articleVoPageInfo;
    }

    @Override
    public AchieveDTO countTotalByUser(Long userId) {
        List<Article> articles = articleExtMapper.findByUser(userId);
        int totalLikeCount = 0, totalViewCount = 0;
        String k_count = KeyUtil.ARTICLE_LIKE_COUNT;
        String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
        for (Article article : articles) {
            String hk_count = KeyUtil.getHashArticleLikeCount(article.getId());
            String hk_viewCount = KeyUtil.getHashArticleViewCount(article.getId());
            Integer reLikeCount = 0, reViewCount = 0;
            if (hashOps.hasKey(k_count, hk_count)) {
                reLikeCount = hashOps.get(k_count, hk_count);
                totalLikeCount += reLikeCount;
            } else {
                totalLikeCount += article.getLikeCount();
            }
            if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
                reViewCount = hashOps.get(k_viewCount, hk_viewCount);
                totalViewCount += reViewCount;
            } else {
                totalViewCount += article.getViewCount();
            }
        }
        AchieveDTO achieveDTO = new AchieveDTO();
        achieveDTO.setTotalLikeCount(totalLikeCount);
        achieveDTO.setTotalViewCount(totalViewCount);
        return achieveDTO;
    }

    @Override
    public int delete(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setDelFlag(ArticleDelStatusEnum.DELETE.getStatus());
        int i = articleMapper.updateByPrimaryKeySelective(article);
        return i;
    }

    @Override
    public Article findById(Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    // 增加点赞数， 根据问题ID
    @Override
    public void incLikeCount(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setLikeCount(1);
        articleExtMapper.incLikeCount(article);
    }

    // 减少点赞数， 根据问题ID
    @Override
    public void decLikeCount(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setLikeCount(1);
        articleExtMapper.decLikeCount(article);
    }

    // 查询点赞数， 根据问题ID
    @Override
    public Integer findLikeCount(Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article.getLikeCount();
    }

    // 根据问题ID增加指定num个like
    /*public void incLikeCount(Long id, Integer num) {
        Article article = new Article();
        article.setId(id);
        article.setLikeCount(num);
        articleExtMapper.incLikeCount(article);
    }*/

    // 推荐文章
    @Override
    public List<Article> selectRelative(ArticleDTO questionDTO) {
        // 标签为空，返回null(发布时处理过)
        if (StringUtils.isEmpty(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String regexTag = StringUtils.replace(questionDTO.getTag(), ",", "|");
        Article article = new Article();
        article.setTag(regexTag);
        article.setId(questionDTO.getId());
        List<Article> articles = articleExtMapper.selectRelative(article);
        for (Article item : articles) {
            String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
            String hk_viewCount = KeyUtil.getHashArticleViewCount(item.getId());
            if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
                item.setViewCount(hashOps.get(k_viewCount, hk_viewCount));
            }
        }
        if (articles != null && articles.size() > 0)
            return articles;
        else
            return null;
    }

    // 其他文章
    @Override
    public List<Article> selectOther(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setAuthorId(articleDTO.getAuthorId());
        article.setId(articleDTO.getId());
        List<Article> articles = articleExtMapper.selectOther(article);
        String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
        for (Article it : articles) {
            String hk_viewCount = KeyUtil.getHashArticleViewCount(it.getId());
            if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
                it.setViewCount(hashOps.get(k_viewCount, hk_viewCount));
            }
        }
        if (articles != null && articles.size() > 0)
            return articles;
        else
            return null;
    }

    @Override
    public boolean lightThumbUp(Long userId, Long articleId, Integer type) {
        String k_status = KeyUtil.ARTICLE_Like_STATUS;
        String hk_status = KeyUtil.getHashArticleLikeStatus(userId, articleId);
        if (hashOps.hasKey(k_status, hk_status)) {// 从缓存中查
            Integer thumbUpStatus = hashOps.get(k_status, hk_status);
            if (thumbUpStatus != null && thumbUpStatus == 1) {// 点赞
                return true;
            }
        } else {
            ThumbUpExample example = new ThumbUpExample();
            example.createCriteria()
                    .andTargetIdEqualTo(articleId)
                    .andUserIdEqualTo(userId)
                    .andTypeEqualTo(type);
            List<ThumbUp> thumbs = thumbUpMapper.selectByExample(example);
            if (thumbs != null && thumbs.size() > 0) {
                return true;
            }
        }
        return false;
    }
}
