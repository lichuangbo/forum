package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.AchieveDTO;
import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.dto.FollowUserDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
import cn.edu.tit.forum.service.impl.FavoriteService;
import cn.edu.tit.forum.service.impl.FollowService;
import cn.edu.tit.forum.service.impl.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/16
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FollowService followService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable("id") String id,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              Model model,
                              HttpSession session) {
        Long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }

        User user = userService.findById(userId);
        if (user == null)
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);

        User sessionUser = (User) session.getAttribute("user");
        Follow follow = followService.find(sessionUser, userId);
        FollowUserDTO followUserDTO = new FollowUserDTO();
        followUserDTO.setUser(user);
        if (follow != null) {
            followUserDTO.setFollowed(true);
        } else {
            followUserDTO.setFollowed(false);
        }

        // 文章
        PageInfo<ArticleDTO> articlePageInfo = articleService.listByUser(page, size, user.getId());
        // 收藏的文章
        PageInfo<ArticleDTO> favoritePageInfo = favoriteService.listByUser(page, size, user.getId());
        // 关注了
        PageInfo<FollowUserDTO> followPageInfo = followService.listByUser(page, size, user.getId(), sessionUser);
        // 关注者
        PageInfo<FollowUserDTO> followerPageInfo = followService.listByTargetUser(page, size, user.getId(), sessionUser);
        // 关注数
        long followUserCount = followService.countFollowUser(user.getId());
        // 成就
        AchieveDTO achieveDTO = articleService.countTotalByUser(user.getId());

        model.addAttribute("followUserDTO", followUserDTO);
        model.addAttribute("articlePageInfo", articlePageInfo);
        model.addAttribute("favoritePageInfo", favoritePageInfo);
        model.addAttribute("followPageInfo", followPageInfo);
        model.addAttribute("followerPageInfo", followerPageInfo);
        model.addAttribute("followUserCount", followUserCount);
        model.addAttribute("achieveDTO", achieveDTO);
        return "user";
    }
}
