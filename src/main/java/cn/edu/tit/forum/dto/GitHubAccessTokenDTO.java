package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * 请求访问令牌参数封装   核心的参数是client_id, client_secret, code
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Data
public class GitHubAccessTokenDTO {
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
}
