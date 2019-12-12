package cn.edu.tit.forum.service;

import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.mapper.CommentMapper;
import cn.edu.tit.forum.mapper.QuestionExtMapper;
import cn.edu.tit.forum.mapper.QuestionMapper;
import cn.edu.tit.forum.model.Comment;
import cn.edu.tit.forum.model.Question;
import exception.CustomizeErrorCode;
import exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/12
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null)
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);

            commentMapper.insert(comment);
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null)
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
