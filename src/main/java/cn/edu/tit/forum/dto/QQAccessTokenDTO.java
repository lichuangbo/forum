package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/13
 */
@Data
public class QQAccessTokenDTO {
    // 申请QQ登录成功后，分配给网站的appid。
    private String client_id;
    // 申请QQ登录成功后，分配给网站的appkey。
    private String client_secret;
    // 返回的authorization code。
    private String code;
    // 与上面一步中传入的redirect_uri保持一致。
    private String redirect_uri;
    // 授权类型，在本步骤中，此值为“authorization_code”。
    private String grant_type;
}
