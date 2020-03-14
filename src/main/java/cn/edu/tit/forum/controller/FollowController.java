package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.FollowService;
import cn.edu.tit.forum.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/3
 */
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/followWriter")
    @ResponseBody
    public ResultDTO followWriter(Long articleId,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        Article article = articleMapper.selectByPrimaryKey(articleId);
        if (article == null)
            return ResultDTO.errorOf(CustomizeErrorCode.ARTICLE_NOT_FOUND);
        User author = userService.findById(article.getAuthorId());
        Follow follow = followService.find(user, article.getAuthorId());
        if (follow != null) {
            followService.delete(user, article.getAuthorId());
            author.setFollowCount(1);
            userService.decFollowCount(author);
            return ResultDTO.okof("关注");
        } else {
            followService.insert(user, article.getAuthorId());
            author.setFollowCount(1);
            userService.incFollowCount(author);
            return ResultDTO.okof("已关注");
        }
    }

    @RequestMapping(value = "/followRecommend",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO followRecommend(Long recommendUserId,
                                     HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        User recommendUser = userService.findById(recommendUserId);
        Follow follow = followService.find(user, recommendUserId);
        if (follow != null) {
            followService.delete(user, recommendUserId);
            recommendUser.setFollowCount(1);
            userService.decFollowCount(recommendUser);
            return ResultDTO.okof("关注");
        } else {
            followService.insert(user, recommendUserId);
            recommendUser.setFollowCount(1);
            userService.incFollowCount(recommendUser);
            return ResultDTO.okof("已关注");
        }
    }
}
