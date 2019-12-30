package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.dto.ThumpUpDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.model.Thumb;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.QuestionService;
import cn.edu.tit.forum.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/29
 */
@Controller
public class ThumbController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ThumbService thumbService;

    @ResponseBody
    @Transactional
    @RequestMapping(value = "/thumb", method = RequestMethod.POST)
    public ResultDTO isThumbUp(@RequestBody ThumpUpDTO thumpUpDTO,
                               HttpServletRequest request,
                               Model model) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);

        Thumb thumb = thumbService.find(user.getId(), thumpUpDTO.getId());
        if (thumb != null) {// 当前用户点击了当前文章
            // 问题表点赞数-1
            questionService.decLikeCount(thumpUpDTO.getId());
            // 点赞表删除对应记录
            Thumb deleteThumb = new Thumb();
            deleteThumb.setUserid(user.getId());
            deleteThumb.setQuestionid(thumpUpDTO.getId());
            thumbService.delete(deleteThumb);
        } else {// 当前用户没有点击当前文章
            // 问题表点赞数+1
            questionService.incLikeCount(thumpUpDTO.getId());
            // 点赞表添加对应记录
            Thumb addThumb = new Thumb();
            addThumb.setUserid(user.getId());
            addThumb.setQuestionid(thumpUpDTO.getId());
            thumbService.insert(addThumb);
        }
        // 查询点赞数
        Integer likeCount = questionService.findLikeCount(thumpUpDTO.getId());
        return ResultDTO.okof(likeCount);
    }
}
