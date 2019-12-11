package exception;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不在了，要不要换一个问题");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
