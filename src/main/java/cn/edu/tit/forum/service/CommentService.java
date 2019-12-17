package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.CommentDTO;
import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.enums.NotifyStatusEnum;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.mapper.*;
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
    private CommentExtMapper commentExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotifyMapper notifyMapper;

    /**
     * 对插入数据库的数据进行校验，做了充足的扩展如问题可删除、评论可删除
     *
     * @param comment Comment对象
     */
    @Transactional
    public void insert(Comment comment, User commentor) {
        // 未选中问题评论
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        // 评论类型错误异常
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {// 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            // 回复的评论找不到异常
            if (dbComment == null)
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);

            // 回复的是评论？这里传的是问题ID，可能是为了最后的点击跳转    不知道传评论内容能不能达到一样的效果
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            // 回复的问题找不到异常，此处复用了一个异常类型
            if (question == null)
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);

            commentMapper.insert(comment);
            // 增加评论回复数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

            // 添加通知
            createNotify(comment, dbComment.getCommentor(), commentor.getName(), comment.getContent(), NotifyTypeEnum.REPLY_COMMENT, question.getId());
        } else {// 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            // 回复的问题找不到异常，此处复用了一个异常类型
            if (question == null)
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            // 增加问题回复数
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);

            // 添加通知
            createNotify(comment, question.getCreater(), commentor.getName(), question.getTitle(), NotifyTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    /**
     * 创建一个通知       添加outerId是为了让profile页，能够发生跳转，太复杂了现在，这个方法
     *
     * @param comment        评论
     * @param receiver       接收人ID
     * @param commentorName  评论人名字
     * @param outerTitle     评论问题的title或者评论的内容
     * @param notifyTypeEnum 通知类型
     * @param outerId         跳转目标
     */
    private void createNotify(Comment comment, Long receiver, String commentorName, String outerTitle, NotifyTypeEnum notifyTypeEnum, Long outerId) {
        // 自己评论自己不用通知
        if (receiver == comment.getCommentor()) {
            return;
        }
        Notify notify = new Notify();
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setType(notifyTypeEnum.getType());
        notify.setOuterId(outerId);
        notify.setNotifier(comment.getCommentor());
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        notify.setReceiver(receiver);
        notify.setOuterTitle(outerTitle);
        notify.setNotifierName(commentorName);
        notifyMapper.insert(notify);
    }

    /**
     * 根据问题ID和类型    获取评论信息或者二级评论信息
     *
     * @param id   id 问题ID
     * @param type 类型（1问题，2评论）
     * @return
     */
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        // 根据问题id和类型 拿到评论信息
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        // 无评论，返回空
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
