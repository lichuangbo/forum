package cn.edu.tit.forum.enums;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/30
 */
public enum ThumbUpTypeEnum {
    QUESTION(0),
    COMMENT(1);

    private Integer type;

    public Integer getType() {
        return type;
    }

    ThumbUpTypeEnum(Integer type) {
        this.type = type;
    }
}
