package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.QuestionDTO;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (("token").equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);

                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        // 重定向到首页后，展示话题集合，将基本信息展示出来
        List<QuestionDTO> list = questionService.queryList();
        model.addAttribute("questionList", list);
        return "index";
    }
}
