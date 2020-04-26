package cn.edu.tit.forum.exception;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    ARTICLE_NOT_FOUND(2001, "你找的文章不在了，要不要换一篇"),// 4
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论，进行回复"),//4
    NO_LOGIN(2003, "当前操作需登录"),// userEdit
    SYS_ERROR(2004, "服务器已经冒烟了，要不然你稍后再试试！！！"),//handleController
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),//4
    COMMENT_NOT_FOUND(2006, "回复的评论不存在"),//4
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFY_FAILE(2008, "读错信息了"),//
    NOTIFY_NOT_FOUND(2009, "通知找不到"),//4
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),//
    INVALID_INPUT(2011, "非法输入"),//
    INVALID_CODE(2012, "验证码无效或已过期，请重新发送"),
    REGISTER_FAIL(2013, "注册失败，请重试"),
    EMAIL_PRESENT(2014, "该邮箱号已被注册"),
    LOGIN_FAIL(2015, "邮箱或密码错误，请重试"),
    TAG_ILLEGAL(2016, "输入的标签非法"),
    USER_NOT_FOUND(2017, "找不到该用户"),
    CURRENT_REQUEST_IS_NOT_ALLOW(2018, "当前请求不合法，请重试"),
    TAG_IS_REPEATED(2019, "标签重复，请检查选择的标签"),
    OLD_PASSWORD_IS_NOT_RIGHT(2020, "原密码不正确，请检查"),
    AUTHORIRY_IS_NOT_ENOUGH(2021, "你的权限不足"),
    ;

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
