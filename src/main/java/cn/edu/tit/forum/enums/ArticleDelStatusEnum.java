package cn.edu.tit.forum.enums;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/4/27
 */
public enum ArticleDelStatusEnum {
    DELETE("1"),
    UN_DELETE("0");

    private String status;

    public String getStatus() {
        return status;
    }

    ArticleDelStatusEnum(String status) {
        this.status = status;
    }
}
