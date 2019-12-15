package cn.edu.tit.forum.enums;

/**
 * 对评论类型进行封装，被评论的只能是问题或评论
 *
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

    /**
     * 判断评论类型是否存在
     *
     * @param type 评论类型
     * @return
     */
    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type)
                return true;
        }
        return false;
    }
}
