package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.CommentA;
import cn.edu.tit.forum.model.Comment;
import cn.edu.tit.forum.model.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/23
 */
public interface ICommentService {
    CommentA listByArticleId(Long parentId, User user);

    void insertComment(Comment comment, User commentor);

    void decLikeCount(Long id);

    void incLikeCount(Long id);

    Integer findLikeCount(Long id);

    int deleteByArticle(Long id);

    int countComment1(Long articleId);
}
