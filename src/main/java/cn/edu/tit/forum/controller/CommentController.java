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

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isEmpty(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
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

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
