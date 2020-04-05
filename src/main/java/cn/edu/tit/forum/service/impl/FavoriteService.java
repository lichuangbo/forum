package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.mapper.FavoriteMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.*;
import cn.edu.tit.forum.service.IFavoriteService;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/2
 */
@Service
public class FavoriteService implements IFavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Integer> hashOps;

    @Override
    public Favorite find(User user, Long articleId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        favoriteExample.createCriteria().andUserIdEqualTo(user.getId()).andArticleIdEqualTo(articleId);
        List<Favorite> favorites = favoriteMapper.selectByExample(favoriteExample);
        if (favorites == null || favorites.size() == 0) {
            return null;
        } else {
            return favorites.get(0);
        }
    }

    @Override
    public void insert(User user, Long articleId) {
        Favorite record = new Favorite();
        record.setArticleId(articleId);
        record.setUserId(user.getId());
        record.setGmtCreate(System.currentTimeMillis());
        favoriteMapper.insert(record);
    }

    @Override
    public void delete(User user, Long articleId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        favoriteExample.createCriteria().andArticleIdEqualTo(articleId).andUserIdEqualTo(user.getId());
        favoriteMapper.deleteByExample(favoriteExample);
    }

    @Override
    public PageInfo<ArticleDTO> listByUser(int page, int size, Long userId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        favoriteExample.createCriteria().andUserIdEqualTo(userId);
        PageHelper.startPage(page, size);
        List<Favorite> favorites = favoriteMapper.selectByExample(favoriteExample);
        List<Article> articles = new ArrayList<>();
        for (Favorite favorite : favorites) {
            Article article = articleMapper.selectByPrimaryKey(favorite.getArticleId());
            // 将收藏时间封装进article
            article.setGmtModified(favorite.getGmtCreate());
            articles.add(article);
        }

        PageInfo<Article> articlePoPageInfo = new PageInfo<>(articles);
        PageInfo<ArticleDTO> articleVoPageInfo = PageUtil.PageInfo2PageInfoVo(articlePoPageInfo);

        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO();
            User user = userMapper.selectByPrimaryKey(article.getAuthorId());
            articleDTO.setUser(user);
            articleDTO.setGmtModified(DateUtil.format(article.getGmtModified()));
            BeanUtils.copyProperties(article, articleDTO);

            String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;
            String hk_viewCount = KeyUtil.getHashArticleViewCount(article.getId());
            if (hashOps.hasKey(k_viewCount, hk_viewCount)) {
                articleDTO.setViewCount(hashOps.get(k_viewCount, hk_viewCount));
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
}
