package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.cache.HotTagCache;
import cn.edu.tit.forum.dto.PageNationDTO;
import cn.edu.tit.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "tag", required = false) String tag,
                        @RequestParam(value = "search", required = false) String search) {
        // 重定向到首页后，展示话题集合，将基本信息展示出来
        PageNationDTO pageNationDTO = questionService.queryList(page, size, tag, search);
        model.addAttribute("pageNationDTO", pageNationDTO);
        model.addAttribute("search", search);
        model.addAttribute("tags", hotTagCache.getHots());
        model.addAttribute("tag", tag);
        return "index";
    }
}
