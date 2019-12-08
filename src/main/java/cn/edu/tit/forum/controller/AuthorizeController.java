package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.AccessTokenDTO;
import cn.edu.tit.forum.dto.GitHubUser;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.provider.GitHubProvider;
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
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.client_secret}")
    private String clientSecret;

    @Value("${github.redirect_uri}")
    private String redirectUri;

    /**
     * GitHub会将设置的临时值和响应代码，返回给指定的站点
     * @param code  GitHub授权码
     * @param state 超链接中设置的临时值
     * @return  跳转的网页
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);

        if (gitHubUser != null) {// 登录成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);

            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
