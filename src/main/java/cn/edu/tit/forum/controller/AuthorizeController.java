package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.AccessTokenDTO;
import cn.edu.tit.forum.dto.GitHubUser;
import cn.edu.tit.forum.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * GitHub会将设置的临时值和响应代码，返回给指定的站点
     * @param code  GitHub授权码
     * @param state 超链接中设置的临时值
     * @return  跳转的网页
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("e10d246abe53066b4519");
        accessTokenDTO.setClient_secret("d8925e480e27293379bc653deefd7420d80ad7c2");
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        System.out.println(gitHubUser);
        return "index";
    }
}
