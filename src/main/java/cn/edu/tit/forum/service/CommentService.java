package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.CommentDTO;
import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.mapper.CommentMapper;
import cn.edu.tit.forum.mapper.QuestionExtMapper;
import cn.edu.tit.forum.mapper.QuestionMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.*;
import exception.CustomizeErrorCode;
import exception.CustomizeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private UserMapper userMapper;

    @Transactional
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

    /**
     * 根据问题ID获取评论信息
     *
     * @param id id
     * @return
     */
    public List<CommentDTO> listByQuestionId(Long id) {
        // 根据问题id和类型 拿到问题评论信息
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        // 根据commentor拿到用户信息
        // 先将commentor去重，可以避免多次查询同一用户
        Set<Long> commentors = comments.stream().map(Comment::getCommentor).collect(Collectors.toSet());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(new ArrayList<>(commentors));
        List<User> users = userMapper.selectByExample(userExample);

        // 将用户信息和评论信息拼装
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
