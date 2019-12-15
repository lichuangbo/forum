package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.CommentCreateDTO;
import cn.edu.tit.forum.dto.CommentDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.model.Comment;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.CommentService;
import exception.CustomizeErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 以post方式回复问题+回复评论
     * 使用ajax的原因：页面局部刷新，用户体验更好
     *
     * @param commentCreateDTO 评论相关dto
     * @param request          获取session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        // 处理用户未登录回复问题
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        // 处理用户回复问题为空
        if (commentCreateDTO == null || StringUtils.isEmpty(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentor(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okof();
    }

    /**
     * 通过get方式获取二级评论信息
     *
     * @param id 评论ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
