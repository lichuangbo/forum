package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);

    void decLikeCount(Comment comment);

    void incLikeCount(Comment comment);
}