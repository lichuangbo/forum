package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.CommentA;
import cn.edu.tit.forum.enums.ThumbUpTypeEnum;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.Favorite;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/10
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ThumbUpService thumbUpService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FollowService followService;

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("/article/{id}")
    public String question(@PathVariable(name = "id") String id,
                           HttpSession session,
                           Model model) {
        // 手动输入异常处理
        Long articleId;
        try {
            articleId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        User user = (User) session.getAttribute("user");

        ArticleDTO articleDTO = articleService.getById(articleId);
        List<Article> relativeArticles = articleService.selectRelative(articleDTO);
        List<Article> otherArticles = articleService.selectOther(articleDTO);
        CommentA commentA = commentService.listByArticleId(articleId, user);
        // 处理是否点亮图标
        if (user != null) {
            boolean lightThumbUp = articleService.lightThumbUp(user.getId(), articleId, ThumbUpTypeEnum.QUESTION.getType());
            if (lightThumbUp)
                model.addAttribute("thumbUp", true);
            /*ThumbUp thumbUp = thumbUpService.find(user.getId(), articleId, ThumbUpTypeEnum.QUESTION.getType());
            if (thumbUp != null)
                model.addAttribute("thumbUp", true);*/

            Favorite favorite = favoriteService.find(user, articleId);
            if (favorite != null)
                model.addAttribute("favorite", true);

            Article article = articleMapper.selectByPrimaryKey(articleId);
            Follow follow = followService.find(user, article.getAuthorId());
            if (follow != null) {
                model.addAttribute("follow", true);
                model.addAttribute("followText", "已关注");
            } else {
                model.addAttribute("followText", "关注");
            }
        }

        articleService.incView(articleId);
        model.addAttribute("articleDTO", articleDTO);
        model.addAttribute("relativeArticles", relativeArticles);
        model.addAttribute("otherArticles", otherArticles);
        model.addAttribute("commentA", commentA);
        return "article";
    }
}
