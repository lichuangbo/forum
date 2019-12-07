package cn.edu.tit.forum.dto;

/**
 * 请求访问令牌参数封装   核心的参数是client_id, client_secret, code
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
public class AccessTokenDTO {
    // 注册时收到的客户端ID
    private String client_id;
    // 注册时收到的客户密码
    private String client_secret;
    // 授权码
    private String code;
    // 重定向uri   授权后让用户跳转到哪里
    private String redirect_uri;
    // 自己提供的随机字符串   防止跨站攻击
    private String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
