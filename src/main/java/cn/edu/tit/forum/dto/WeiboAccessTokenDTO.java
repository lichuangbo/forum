package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/8
 */
@Data
public class WeiboAccessTokenDTO {
    // 申请应用时分配的AppKey
    private String client_id;
    // 申请应用时分配的AppSecret
    private String client_secret;
    // 调用authorize获得的code值
    private String code;
    // 回调地址，需需与注册应用里的回调地址一致
    private String redirect_uri;
    // 请求的类型，填写authorization_code
    private String grant_type;
}
