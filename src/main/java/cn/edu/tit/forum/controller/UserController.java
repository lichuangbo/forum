package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.*;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
import cn.edu.tit.forum.service.impl.FavoriteService;
import cn.edu.tit.forum.service.impl.FollowService;
import cn.edu.tit.forum.service.impl.UserService;
import cn.edu.tit.forum.utils.MD5Util;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userEdit/{id}")
    public String userEdit(@PathVariable("id") String id,
                           HttpSession session,
                           Model model) {
        Long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        User user = userService.findById(userId);
        if (user == null)
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);

        model.addAttribute("user", user);
        return "userEdit";
    }

    @RequestMapping(value = "/getAvatar", method = RequestMethod.POST)
    public String getAvatar(HttpSession session,
                            Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "userEdit::avatar-img-upload";
    }

    @RequestMapping("/modifyAvatar")
    @ResponseBody
    public ResultDTO modifyAvatar(@RequestParam(name = "avatarUrl") String avatarUrl,
                                  HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        user.setAvatarUrl(avatarUrl);
        int count = userService.updateUser(user);
        if (count == 1)
            return ResultDTO.okof(avatarUrl);
        else
            return null;
    }

    @RequestMapping("/saveProfile")
    @ResponseBody
    public ResultDTO saveProfile(User user,
                                 HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        sessionUser.setNickname(user.getNickname());
        sessionUser.setDescript(user.getDescript());
        int updateCount = userService.updateUser(sessionUser);
        if (updateCount == 1)
            return ResultDTO.okof(sessionUser);
        else
            return null;
    }

    @RequestMapping("/savePassword")
    @ResponseBody
    public ResultDTO savePassword(String oldPassword,
                                  String newPassword,
                                  HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        String _oldPassword = MD5Util.encode2hex(oldPassword);
        if (!_oldPassword.equals(sessionUser.getEmailPassword())) {
            return ResultDTO.errorOf(CustomizeErrorCode.OLD_PASSWORD_IS_NOT_RIGHT);
        }

        String _newPassword = MD5Util.encode2hex(newPassword);
        sessionUser.setEmailPassword(_newPassword);
        int updateCount = userService.updateUser(sessionUser);
        if (updateCount == 1)
            return ResultDTO.okof();
        else
            return null;
    }
}
