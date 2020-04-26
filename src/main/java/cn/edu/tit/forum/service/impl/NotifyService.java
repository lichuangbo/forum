package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.NotifyDTO;
import cn.edu.tit.forum.enums.NotifyStatusEnum;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.mapper.CommentMapper;
import cn.edu.tit.forum.mapper.NotifyMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.*;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.service.INotifyService;
import cn.edu.tit.forum.utils.DateUtil;
import cn.edu.tit.forum.utils.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
@Service
public class NotifyService implements INotifyService {

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    // 检索未读通知数量
    @Override
    public Long unreadCount(Long userId) {
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotifyStatusEnum.UNREAD.getStatus());
        return notifyMapper.countByExample(notifyExample);
    }

    // 用户点击跳转
    @Override
    public Notify read(Long id, User user) {
        Notify notify = notifyMapper.selectByPrimaryKey(id);
        if (notify == null)
            throw new CustomizeException(CustomizeErrorCode.NOTIFY_NOT_FOUND);
        if (!notify.getReceiver().equals(user.getId()))
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFY_FAILE);
        return notify;
    }

    @Override
    public void notifyAuthor(Long managerId, Long receiveUserId, Long outerId, NotifyTypeEnum notifyTypeEnum, String notifierName, String outerTitle) {
        // 管理员删除自己文章不用通知
        if (managerId.equals(receiveUserId)) {
            return;
        }
        Notify notify = new Notify();
        notify.setNotifier(managerId);
        notify.setReceiver(receiveUserId);
        notify.setOuterId(outerId);
        notify.setType(notifyTypeEnum.getType());
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        notify.setNotifierName(notifierName);
        notify.setOuterTitle(outerTitle);
        notifyMapper.insert(notify);
    }

    // 通知问题被点赞用户
    @Override
    public void notifyRespArticleUser(Long userId, Long articleId) {
        Notify notify = new Notify();
        notify.setNotifier(userId);
        Article article = articleMapper.selectByPrimaryKey(articleId);
        // 自己点赞不用通知自己
        if (article.getAuthorId().equals(userId)) {
            return;
        }
        notify.setReceiver(article.getAuthorId());
        notify.setOuterId(articleId);
        notify.setType(NotifyTypeEnum.LIKE_ARTICLE.getType());
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        User user = userMapper.selectByPrimaryKey(userId);
        notify.setNotifierName(user.getNickname());
        notify.setOuterTitle(article.getTitle());
        notifyMapper.insert(notify);
    }

    // 通知评论被点赞用户
    @Override
    public void notifyRespCommentUser(Long userId, Long commentId) {
        Notify notify = new Notify();
        notify.setNotifier(userId);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        notify.setReceiver(comment.getUserId());

        notify.setOuterTitle(comment.getContent());
        while (comment.getType() != 1) {
            comment = commentMapper.selectByPrimaryKey(comment.getParentId());
        }
        Article article = articleMapper.selectByPrimaryKey(comment.getParentId());
        notify.setOuterId(article.getId());
        notify.setType(NotifyTypeEnum.LIKE_COMMENT.getType());
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        User user = userMapper.selectByPrimaryKey(userId);
        notify.setNotifierName(user.getNickname());
        notifyMapper.insert(notify);
    }

    // 取消文章通知
    @Override
    public void cancelArticleNotify(Long targetId, Integer type) {
        Article article = articleMapper.selectByPrimaryKey(targetId);
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andOuterTitleEqualTo(article.getTitle()).andTypeEqualTo(type);
        notifyMapper.deleteByExample(notifyExample);
    }

    // 取消评论通知
    @Override
    public void cancelCommentNotify(Long targetId, Integer type) {
        Comment comment = commentMapper.selectByPrimaryKey(targetId);
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andOuterTitleEqualTo(comment.getContent()).andTypeEqualTo(type);
        notifyMapper.deleteByExample(notifyExample);
    }

    // 检索当前用户所有通知
    @Override
    public PageInfo<NotifyDTO> queryList(Long id, Integer page, Integer size) {
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andReceiverEqualTo(id);
        notifyExample.setOrderByClause("gmt_create desc");
        PageHelper.startPage(page, size);
        List<Notify> notifies = notifyMapper.selectByExample(notifyExample);

        PageInfo<Notify> notifyPoPageInfo = new PageInfo<>(notifies);
        PageInfo<NotifyDTO> notifyVoPageInfo = PageUtil.PageInfo2PageInfoVo(notifyPoPageInfo);

        List<NotifyDTO> notifyDTOS = new ArrayList<>();
        for (Notify notify : notifies) {
            NotifyDTO notifyDTO = new NotifyDTO();
            User user = userMapper.selectByPrimaryKey(notify.getNotifier());
            notifyDTO.setUser(user);
            notifyDTO.setGmtCreate(DateUtil.format(notify.getGmtCreate()));
            BeanUtils.copyProperties(notify, notifyDTO);
            notifyDTO.setNotifyType(NotifyTypeEnum.nameOfType(notify.getType()));

            notifyDTOS.add(notifyDTO);
        }
        for (NotifyDTO notifyDTO : notifyDTOS) {
            notifyVoPageInfo.getList().add(notifyDTO);
        }
        return notifyVoPageInfo;
    }

    // 当前用户读取所有通知
    @Override
    public void readNotify(Long userId) {
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria()
                .andStatusEqualTo(NotifyStatusEnum.UNREAD.getStatus())
                .andReceiverEqualTo(userId);
        List<Notify> notifies = notifyMapper.selectByExample(notifyExample);
        for (Notify notify : notifies) {
            notify.setStatus(NotifyStatusEnum.READ.getStatus());
            notifyMapper.updateByPrimaryKey(notify);
        }
    }
}
