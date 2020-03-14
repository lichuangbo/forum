package cn.edu.tit.forum.provider;

import cn.edu.tit.forum.dto.GitHubAccessTokenDTO;
import cn.edu.tit.forum.dto.GitHubUser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 第三方支持
 *
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Component
@Slf4j
public class GitHubProvider {
    public String getAccessToken(GitHubAccessTokenDTO accessTokenDTO) {
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
            log.error("get GitHub access_token error, {}", e);
        }
        return null;
    }

    /**
     * 发送Get请求，通过access_token获取用户信息
     *
     * @param accessToken access_token
     * @return 用户信息
     */
    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        // 即将废弃
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token=" + accessToken)
//                .build();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();// json格式，需转换
            GitHubUser gitHubUser = JSON.parseObject(str, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            log.error("get GitHub User error, {}", e);
        }
        return null;
    }
}
