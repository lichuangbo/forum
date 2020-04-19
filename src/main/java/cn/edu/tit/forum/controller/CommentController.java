package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.CommentA;
import cn.edu.tit.forum.dto.CommentCreateDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.model.Comment;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
import cn.edu.tit.forum.service.impl.CommentService;
import cn.edu.tit.forum.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
@Controller
public class CommentController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultDTO post(CommentCreateDTO commentCreateDTO,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 处理用户未登录回复问题
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        // 处理用户回复问题为空
        if (commentCreateDTO == null || StringUtils.isEmpty(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        comment.setUserId(user.getId());
        comment.setRespUserId(commentCreateDTO.getRespUserId());
        comment.setGmtCreate(System.currentTimeMillis());
        if (commentCreateDTO.getRespUserId() == null) {
            ArticleDTO articleDTO = articleService.getById(commentCreateDTO.getParentId());
            User author = userService.findById(articleDTO.getAuthorId());
            comment.setRespUserId(author.getId());
        }
        commentService.insertComment(comment, user);

        if (commentCreateDTO.getType() == 1) {
            int i = commentService.countComment1(commentCreateDTO.getParentId());
            return ResultDTO.okof(i);
        } else {
            return ResultDTO.okof();
        }
    }

    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    public String getComment(Long articleId,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        CommentA commentA = commentService.listByArticleId(articleId, user);
        model.addAttribute("commentA", commentA);
        return "article::comment-list";
    }
}
