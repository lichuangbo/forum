package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.cache.HotTagCache;
import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.RecommendAuthorDTO;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
import cn.edu.tit.forum.service.impl.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "tag", required = false) String tag,
                        @RequestParam(value = "search", required = false) String search,
                        HttpSession session) {
        PageInfo<ArticleDTO> pageInfo = articleService.queryList(page, size, tag, search);
        User user = (User) session.getAttribute("user");
        if (user == null)
            user = new User();
        List<RecommendAuthorDTO> recommendAuthorDTOS = userService.recommendAuthor(user);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        model.addAttribute("hotTags", hotTagCache.getHotTag());
        model.addAttribute("recommendAuthors", recommendAuthorDTOS);
        model.addAttribute("tag", tag);
        return "index";
    }

    @RequestMapping(value = "/getRecommend", method = RequestMethod.POST)
    public String loadRecommend(HttpSession session,
                                Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            user = new User();
        List<RecommendAuthorDTO> recommendAuthorDTOS = userService.recommendAuthor(user);
        model.addAttribute("recommendAuthors", recommendAuthorDTOS);
        return "index::recommend-list";
    }
}
