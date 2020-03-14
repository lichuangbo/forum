package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
@Data
public class NotifyDTO {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    // 通知类型
    private String notifyType;
    // 用户信息
    private User user;
}
