package cn.edu.tit.forum.exception;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
public class CustomizeException extends RuntimeException {
    // 异常提示信息
    private String message;
    // 存储异常枚举中的code
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
