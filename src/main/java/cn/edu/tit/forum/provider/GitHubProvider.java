package cn.edu.tit.forum.provider;

import cn.edu.tit.forum.dto.AccessTokenDTO;
import cn.edu.tit.forum.dto.GitHubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 第三方支持
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Component
public class GitHubProvider {
    /**
     * 发送post请求获取access_token
     * @param accessTokenDTO   client_id, client_secret, code封装的实体类
     * @return access_token值
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送Get请求，通过access_token获取用户信息
     * @param accessToken access_token
     * @return 用户信息
     */
    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();// json格式，需转换
            GitHubUser gitHubUser = JSON.parseObject(str, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
