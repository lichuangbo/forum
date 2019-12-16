package cn.edu.tit.forum.enums;

/**
 * 通知状态
 *
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
public enum NotifyStatusEnum {
    UNREAD(0),
    READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotifyStatusEnum(int status) {
        this.status = status;
    }
}
