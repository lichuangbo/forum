package cn.edu.tit.forum.enums;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
public enum NotifyTypeEnum {
    REPLY_ARTICLE(1, "评论了文章"),
    REPLY_COMMENT(2, "回复了评论"),
    LIKE_ARTICLE(3, "点赞了文章"),
    LIKE_COMMENT(4, "点赞了评论"),
    ILLEGAL_ARTICLE(5, "删除了你的文章"),
    ILLEGAL_COMMENT(6, "删除了你的评论"),
    SYSTEM_NOTIFY(7, "发布了论坛公告")
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotifyTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotifyTypeEnum notifyTypeEnum : NotifyTypeEnum.values()) {
            if (notifyTypeEnum.getType() == type) {
                return notifyTypeEnum.getName();
            }
        }
        return "";
    }
}
