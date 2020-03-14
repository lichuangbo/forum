package cn.edu.tit.forum.provider;

import cn.edu.tit.forum.dto.QQAccessTokenDTO;
import cn.edu.tit.forum.dto.QQUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/13
 */
@Component
@Slf4j
public class QQProvider {
    public String getAccessToken(QQAccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        String s = "client_id=" + accessTokenDTO.getClient_id()
                + "&client_secret=" + accessTokenDTO.getClient_secret()
                + "&grant_type=" + accessTokenDTO.getGrant_type()
                + "&redirect_uri=" + accessTokenDTO.getRedirect_uri()
                + "&code=" + accessTokenDTO.getCode();
        RequestBody body = RequestBody.create(mediaType, s);
        Request request = new Request.Builder()
                .url("https://graph.qq.com/oauth2.0/token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String accessToken = str.split("&")[0].split("=")[1];
            return accessToken;
        } catch (IOException e) {
            log.error("get QQ access_token error, {}", e);
        }
        return null;
    }

    /**
     * 获取openid
     * openid是此网站上唯一对应用户身份的标识，网站可将此ID进行存储便于用户下次登录时辨识其身份，或将其与用户在网站上的原有账号进行绑定。
     *
     * @param access_token 在Step1中获取到的access token。
     * @return openid
     */
    public String getOpenID(String access_token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://graph.qq.com/oauth2.0/me?access_token=" + access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            String jsonString = str.split(" ")[1].split(" ")[0];
            JSONObject obj = JSONObject.parseObject(jsonString);
            String openid = obj.getString("openid");
            return openid;
        } catch (IOException e) {
            log.error("get QQ openid error, {}", e);
        }
        return null;
    }

    /**
     * 获取qq用户信息
     *
     * @param access_token       可通过使用Authorization_Code获取Access_Token 或来获取。access_token有3个月有效期。
     * @param oauth_consumer_key 申请QQ登录成功后，分配给应用的appid
     * @param openid             用户的ID，与QQ号码一一对应。
     * @return QQUser
     */
    public QQUser getUser(String access_token, String oauth_consumer_key, String openid) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://graph.qq.com/user/get_user_info?access_token="
                        + access_token + "&oauth_consumer_key=" + oauth_consumer_key + "&openid=" + openid)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            QQUser qqUser = JSON.parseObject(str, QQUser.class);
            qqUser.setOpenId(openid);
            return qqUser;
        } catch (IOException e) {
            log.error("get QQ user error, {}", e);
        }
        return null;
    }
}
