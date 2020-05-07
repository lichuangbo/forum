package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.FollowMessageDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
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
    private ArticleService articleService;

    @RequestMapping("/followWriter")
    @ResponseBody
    public ResultDTO followWriter(Long articleId,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        Article article = articleService.findById(articleId);
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
    public ResultDTO followRecommend(String userId,
                                     HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        Long id = Long.parseLong(userId);
        User recommendUser = userService.findById(id);
        Follow follow = followService.find(user, id);
        if (follow != null) {
            followService.delete(user, id);
            recommendUser.setFollowCount(1);
            userService.decFollowCount(recommendUser);
            return ResultDTO.okof("关注");
        } else {
            followService.insert(user, id);
            recommendUser.setFollowCount(1);
            userService.incFollowCount(recommendUser);
            return ResultDTO.okof("已关注");
        }
    }

    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO followUser(Long userPageId,
                                Long userId,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);

        User pageUser = userService.findById(userPageId);
        User followedUser = userService.findById(userId);
        Follow follow = followService.find(user, userId);
        FollowMessageDTO followMessageDTO = new FollowMessageDTO();
        if (follow != null) {
            followService.delete(user, userId);
            followedUser.setFollowCount(1);
            userService.decFollowCount(followedUser);
            followMessageDTO.setMessage("关注");
        } else {
            followService.insert(user, userId);
            followedUser.setFollowCount(1);
            userService.incFollowCount(followedUser);
            followMessageDTO.setMessage("已关注");
        }
        User pageUser2 = userService.findById(pageUser.getId());
        followMessageDTO.setFollowerCount(pageUser2.getFollowCount());
        long count = followService.countFollowUser(pageUser2.getId());
        followMessageDTO.setFollowedCount(count);
        return ResultDTO.okof(followMessageDTO);
    }
}
