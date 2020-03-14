package cn.edu.tit.forum.provider;

import cn.edu.tit.forum.dto.WeiboAccessTokenDTO;
import cn.edu.tit.forum.dto.WeiboUser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/8
 */
@Component
@Slf4j
public class WeiboProvider {
    /**
     * 获取access_token和uid json串
     * @param weiboAccessTokenDTO 请求信息
     * @return
     */
    public String getAccessTokenAndUid(WeiboAccessTokenDTO weiboAccessTokenDTO) {
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String s = "client_id=" + weiboAccessTokenDTO.getClient_id()
                + "&client_secret=" + weiboAccessTokenDTO.getClient_secret()
                + "&grant_type=" + weiboAccessTokenDTO.getGrant_type()
                + "&redirect_uri=" + weiboAccessTokenDTO.getRedirect_uri()
                + "&code=" + weiboAccessTokenDTO.getCode();
        RequestBody body = RequestBody.create(mediaType, s);
        Request request = new Request.Builder()
                .url("https://api.weibo.com/oauth2/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            return str;
        } catch (Exception e) {
            log.error("get Weibo access_token and uid error, {}", e);
        }
        return null;
    }

    /**
     * 获取用户信息
     * @param accessToken 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param uid 需要查询的用户ID。
     * @return
     */
    public WeiboUser getUser(String accessToken, String uid) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            WeiboUser weiboUser = JSON.parseObject(str, WeiboUser.class);
            return weiboUser;
        } catch (IOException e) {
            log.error("get Weibo User error, {}", e);
        }
        return null;
    }
}
