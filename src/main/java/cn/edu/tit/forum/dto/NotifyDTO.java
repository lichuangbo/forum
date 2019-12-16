package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
@Data
public class NotifyDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    // 通知的人
    private Long notifier;
    private String notifierName;
    // 通知的名称
    private String outerTitle;
    private Long outerId;

    // 通知类型
    private String typeName;
    private Integer type;
}
