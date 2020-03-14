package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.cache.TagCache;
import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/8
 */
@Controller
public class PublishController {

    @Autowired
    private ArticleService articleService;

    // 写文章跳转
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    // 发布文章
    @PostMapping("/publish")
    @ResponseBody
    public ResultDTO doPublish(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("tag") String tag,
                               @RequestParam("id") Long id,
                               HttpSession session,
                               Model model) {
        // 表单值回显（用户写入一部分，就点击提交，会得到不合法信息，同时保留了先前填入的值）
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        String invalid = TagCache.filterInvalid(tag);
        if (!StringUtils.isEmpty(invalid)) {
            return ResultDTO.errorOf(CustomizeErrorCode.TAG_ILLEGAL);
        }

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setTag(tag);
        article.setAuthorId(user.getId());
        // 前端的处理是一个隐藏输入框，为了将它传到后端   后端要利用它来判断question是编辑还是发布
        article.setId(id);
        articleService.createOrUpdate(article);
        return ResultDTO.okof();
    }

    // 编辑文章
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        ArticleDTO articleDTO = articleService.getById(id);
        model.addAttribute("title", articleDTO.getTitle());
        model.addAttribute("content", articleDTO.getContent());
        model.addAttribute("tag", articleDTO.getTag());
        model.addAttribute("id", articleDTO.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
