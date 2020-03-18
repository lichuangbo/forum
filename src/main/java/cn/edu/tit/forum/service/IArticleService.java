package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.AchieveDTO;
import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.model.Article;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/19
 */
public interface IArticleService {
    void createOrUpdate(Article article);

    PageInfo<ArticleDTO> queryList(Integer page, Integer size, String tag, String search);

    ArticleDTO getById(Long id);

    void incLikeCount(Long id);

    void decLikeCount(Long id);

    Integer findLikeCount(Long id);

    List<Article> selectRelative(ArticleDTO articleDTO);

    List<Article> selectOther(ArticleDTO articleDTO);

    boolean lightThumbUp(Long userId, Long articleId, Integer type);

    void incView(Long articleId);

    PageInfo<ArticleDTO> listByUser(int page, int size, Long userId);

    AchieveDTO countTotalByUser(Long userId);
}
