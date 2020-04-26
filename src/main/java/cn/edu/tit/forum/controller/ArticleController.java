package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.CommentA;
import cn.edu.tit.forum.dto.FollowUserDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.enums.ThumbUpTypeEnum;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.Favorite;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.INotifyService;
import cn.edu.tit.forum.service.impl.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private INotifyService notifyService;

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

    @RequestMapping("/deleteArticle")
    @ResponseBody
    @Transactional
    public ResultDTO deleteArticle(Long id,
                                   HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null)
            return ResultDTO.errorOf(CustomizeErrorCode.ARTICLE_NOT_FOUND);
        if (!article.getAuthorId().equals(sessionUser.getId()))
            return ResultDTO.errorOf(CustomizeErrorCode.CURRENT_REQUEST_IS_NOT_ALLOW);

        // 删除文章
        int delArtiCount = articleService.delete(article.getId());
        // 删除文章相关收藏
        int delFavCount = favoriteService.deleteByArticle(article.getId());
        // 删除文章相关评论
        int delCommCount = commentService.deleteByArticle(article.getId());

        return ResultDTO.okof("删除成功");
    }

    @RequestMapping("/deleteArticleByManager")
    @ResponseBody
    @Transactional
    public ResultDTO deleteByManager(@RequestParam("id") Long id,
                                     HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (sessionUser.getRole().equals("0")) {
            return ResultDTO.errorOf(CustomizeErrorCode.AUTHORIRY_IS_NOT_ENOUGH);
        }
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.ARTICLE_NOT_FOUND);
        }

        // 通知作者文章被管理员删除
        notifyService.notifyAuthor(sessionUser.getId(), article.getAuthorId(), article.getId(), NotifyTypeEnum.ILLEGAL_ARTICLE, sessionUser.getNickname(), article.getTitle());

        // 删除文章
        int delArtiCount = articleService.delete(article.getId());
        // 删除文章相关收藏
        int delFavCount = favoriteService.deleteByArticle(article.getId());
        return ResultDTO.okof("删除成功");
    }

    @RequestMapping("/getArticleList")
    public String getArticleList(Long id,
                                 HttpSession session,
                                 Model model) {
        User sessionUser = (User) session.getAttribute("user");
        PageInfo<ArticleDTO> articlePageInfo = articleService.listByUser(1, 10, sessionUser.getId());

        User user = userService.findById(id);
        FollowUserDTO followUserDTO = new FollowUserDTO();
        followUserDTO.setUser(user);

        model.addAttribute("articlePageInfo", articlePageInfo);
        model.addAttribute("followUserDTO", followUserDTO);
        return "user::personal-article-list";
    }
}
