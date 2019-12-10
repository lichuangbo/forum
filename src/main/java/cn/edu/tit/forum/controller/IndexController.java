package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.PageNationDTO;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "3") Integer size) {

        // 重定向到首页后，展示话题集合，将基本信息展示出来
        PageNationDTO pageNationDTO = questionService.queryList(page, size);
        model.addAttribute("pageNationDTO", pageNationDTO);
        return "index";
    }
}
