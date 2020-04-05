package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.model.Favorite;
import cn.edu.tit.forum.model.User;
import com.github.pagehelper.PageInfo;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/2
 */
public interface IFavoriteService {
    Favorite find(User user, Long articleId);

    void insert(User user, Long articleId);

    void delete(User user, Long articleId);

    PageInfo<ArticleDTO> listByUser(int page, int size, Long userId);

    int deleteByArticle(Long id);
}
