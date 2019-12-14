package cn.edu.tit.forum.enums;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/12
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type)
                return true;
        }
        return false;
    }
}
