package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.NotifyDTO;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.model.Notify;
import cn.edu.tit.forum.model.User;
import com.github.pagehelper.PageInfo;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/1
 */
public interface INotifyService {
    void notifyRespArticleUser(Long userId, Long articleId);

    void notifyRespCommentUser(Long userId, Long commentId);

    void cancelArticleNotify(Long targetId, Integer type);

    void cancelCommentNotify(Long targetId, Integer type);

    Long unreadCount(Long userId);

    PageInfo<NotifyDTO> queryList(Long id, Integer page, Integer size);

    void readNotify(Long userId);

    Notify read(Long id, User user);

    void notifyAuthor(Long managerId, Long receiveUserId, Long outerId, NotifyTypeEnum notifyTypeEnum, String notifierName, String outerTitle);
}
