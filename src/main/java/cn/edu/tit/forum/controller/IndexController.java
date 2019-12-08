package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (("token").equals(cookie.getName())){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);

                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }
}
