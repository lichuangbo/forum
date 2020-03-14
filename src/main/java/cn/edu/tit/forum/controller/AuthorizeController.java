package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.*;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.provider.GitHubProvider;
import cn.edu.tit.forum.provider.QQProvider;
import cn.edu.tit.forum.provider.WeiboProvider;
import cn.edu.tit.forum.service.impl.UserService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 用户授权
 *
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private WeiboProvider weiboProvider;

    @Autowired
    private QQProvider qqProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client_id}")
    private String github_clientId;

    @Value("${github.client_secret}")
    private String github_secret;

    @Value("${github.redirect_uri}")
    private String github_redirectUri;

    @Value("${weibo.client_id}")
    private String weibo_clientId;

    @Value("${weibo.client_secret}")
    private String weibo_clientSecret;

    @Value("${weibo.redirect_uri}")
    private String weibo_redirectUri;

    @Value("${qq.appid}")
    private String qq_appId;

    @Value("${qq.appkey}")
    private String qq_appKey;

    @Value("${qq.redirect_uri}")
    private String qq_redirectUri;

    @GetMapping("/githubcallback")
    public String github_callback(@RequestParam(name = "code") String code,
                                  @RequestParam(name = "state") String state,
                                  HttpServletResponse response) {
        GitHubAccessTokenDTO accessTokenDTO = new GitHubAccessTokenDTO();
        accessTokenDTO.setClient_id(github_clientId);
        accessTokenDTO.setClient_secret(github_secret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(github_redirectUri);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);

        if (gitHubUser != null && gitHubUser.getId() != null) {// 登录成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setNickname(gitHubUser.getName());
            user.setDescript(gitHubUser.getBio());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user.setCredential(token);
            user.setIdentifyType("GitHub");
            user.setIdentifier(gitHubUser.getId());
            userService.createOrUpdate(user);

            Cookie cookie = new Cookie("token", token);
            // 设置cookie过期时间 3天
            cookie.setMaxAge(60 * 60 * 24 * 3);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            log.error("callback get githubUser error, {}", gitHubUser);
            return "redirect:/";
        }
    }

    @GetMapping("/weibocallback")
    public String weibo_callback(@RequestParam(name = "code") String code,
                                 HttpServletResponse response) {
        WeiboAccessTokenDTO accessTokenDTO = new WeiboAccessTokenDTO();
        accessTokenDTO.setClient_id(weibo_clientId);
        accessTokenDTO.setClient_secret(weibo_clientSecret);
        accessTokenDTO.setGrant_type("authorization_code");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(weibo_redirectUri);
        String accessJson = weiboProvider.getAccessTokenAndUid(accessTokenDTO);
        String access_token = JSONObject.parseObject(accessJson).getString("access_token");
        String uid = JSONObject.parseObject(accessJson).getString("uid");
        WeiboUser weiboUser = weiboProvider.getUser(access_token, uid);

        if (weiboUser != null && weiboUser.getIdstr() != null) {// 登录成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setNickname(weiboUser.getScreen_name());
            user.setDescript(weiboUser.getDescription());
            user.setAvatarUrl(weiboUser.getAvatar_hd());
            user.setIdentifyType("Weibo");
            user.setIdentifier(weiboUser.getIdstr());
            user.setCredential(token);
            userService.createOrUpdate(user);

            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 3);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            log.error("callback get weiboUser error, {}", weiboUser);
            return "redirect:/";
        }
    }

    @GetMapping("/qqcallback")
    public String qq_callback(@RequestParam(name = "code") String code,
                              HttpServletResponse response) {
        QQAccessTokenDTO accessTokenDTO = new QQAccessTokenDTO();
        accessTokenDTO.setClient_id(qq_appId);
        accessTokenDTO.setClient_secret(qq_appKey);
        accessTokenDTO.setGrant_type("authorization_code");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(qq_redirectUri);
        String access_token = qqProvider.getAccessToken(accessTokenDTO);
        String openID = qqProvider.getOpenID(access_token);
        QQUser qqUser = qqProvider.getUser(access_token, qq_appId , openID);

        if (qqUser != null && qqUser.getOpenId() != null) {// 登录成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setNickname(qqUser.getNickname());
            user.setDescript("暂无描述");
            user.setAvatarUrl(qqUser.getFigureurl_qq_1());
            user.setCredential(token);
            user.setIdentifyType("QQ");
            user.setIdentifier(qqUser.getOpenId());
            userService.createOrUpdate(user);

            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 3);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            log.error("callback get qqUser error, {}", qqUser);
            return "redirect:/";
        }
    }
}
