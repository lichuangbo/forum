package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.CommentCreateDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.model.Comment;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.CommentService;
import exception.CustomizeErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentor(user.getId());
        commentService.insert(comment);
        return ResultDTO.okof();
    }
}
