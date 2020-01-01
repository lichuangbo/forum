package cn.edu.tit.forum.service;

import cn.edu.tit.forum.enums.NotifyStatusEnum;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.mapper.*;
import cn.edu.tit.forum.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/29
 */
@Service
public class ThumbService {

    @Autowired
    private ThumbMapper thumbMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private CommentMapper commentMapper;

    // 添加点赞记录
    public int insert(Thumb thumb) {
        return thumbMapper.insert(thumb);
    }

    // 删除点赞记录
    public int delete(Thumb thumb, Integer type) {
        ThumbExample example = new ThumbExample();
        example.createCriteria()
                .andTypeidEqualTo(thumb.getTypeid())
                .andUseridEqualTo(thumb.getUserid())
                .andTypeEqualTo(type);
        return thumbMapper.deleteByExample(example);
    }

    // 查询是否点赞
    public Thumb find(Long userId, Long typeId, Integer type) {
        ThumbExample example = new ThumbExample();
        example.createCriteria()
                .andTypeidEqualTo(typeId)
                .andUseridEqualTo(userId)
                .andTypeEqualTo(type);
        List<Thumb> thumbs = thumbMapper.selectByExample(example);
        if (thumbs == null || thumbs.size() == 0) {// 没有点赞
            return null;
        } else {// 用户点赞
            return thumbs.get(0);
        }
    }

    // 通知问题被点赞用户
    public void notifyThumbQues(Long userId, Long questionId) {
        Notify notify = new Notify();
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setType(NotifyTypeEnum.LIKE_QUESTION.getType());
        notify.setNotifier(userId);
        notify.setOuterId(questionId);
        Question question = questionMapper.selectByPrimaryKey(questionId);
        // 自己点赞不用通知自己
        if (question.getCreater() == userId) {
            return;
        }
        notify.setReceiver(question.getCreater());
        notify.setOuterTitle(question.getTitle());
        User user = userMapper.selectByPrimaryKey(userId);
        notify.setNotifierName(user.getName());
        notifyMapper.insert(notify);
    }

    // 通知评论被点赞用户
    public void notifyThumbComm(Long userId, Long commentId) {
        Notify notify = new Notify();
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setType(NotifyTypeEnum.LIKE_COMMENT.getType());
        notify.setNotifier(userId);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        notify.setOuterId(comment.getParentId());
        notify.setOuterTitle(comment.getContent());
        notify.setReceiver(comment.getCommentor());
        User user = userMapper.selectByPrimaryKey(userId);
        notify.setNotifierName(user.getName());
        notifyMapper.insert(notify);
    }
}
