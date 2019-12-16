package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.PageNationDTO;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.NotifyService;
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
    private QuestionService questionService;

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("sections", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageNationDTO pageNationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pageNationDTO", pageNationDTO);
        } else if ("replies".equals(action)) {
            PageNationDTO pageNationDTO = notifyService.list(user.getId(), page, size);

            Long unreadCount = notifyService.unreadCount(user.getId());
            model.addAttribute("pageNationDTO", pageNationDTO);
            model.addAttribute("unreadCount", unreadCount);
            model.addAttribute("sections", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }
}
