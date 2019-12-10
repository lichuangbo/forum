package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.PageNationDTO;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/10
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "3") Integer size) {
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (("token").equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);

                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("sections", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("sections", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PageNationDTO pageNationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pageNationDTO", pageNationDTO);
        return "profile";
    }
}
