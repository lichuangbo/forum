package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.CommentDTO;
import cn.edu.tit.forum.dto.QuestionDTO;
import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.model.Thumb;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.CommentService;
import cn.edu.tit.forum.service.QuestionService;
import cn.edu.tit.forum.service.ThumbService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/10
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ThumbService thumbService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id,
                           HttpServletRequest request,
                           Model model) {
        Long questionId;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }

        // 处理是否点亮点赞图标
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            Thumb thumb = thumbService.find(user.getId(), questionId);
            if (thumb != null) {
                model.addAttribute("click", thumb);
            }
        }

        QuestionDTO questionDTO = questionService.getById(questionId);
        List<QuestionDTO> relativeQuestions = questionService.selectRelative(questionDTO);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        // 每次访问都会增加阅读数，每次刷新也是   一个BUG，待解决
        questionService.incView(questionId);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relativeQuestions", relativeQuestions);
        return "question";
    }
}
