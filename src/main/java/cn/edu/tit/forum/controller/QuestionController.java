package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.CommentDTO;
import cn.edu.tit.forum.dto.QuestionDTO;
import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.service.CommentService;
import cn.edu.tit.forum.service.QuestionService;
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

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        // 增加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        return "question";
    }
}
