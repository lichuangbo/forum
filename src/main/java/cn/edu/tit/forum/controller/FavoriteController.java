package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.model.Favorite;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/2
 */
@Controller
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @ResponseBody
    @RequestMapping(value = "/favoriteArticle", method = RequestMethod.POST)
    public ResultDTO favoriteArticle(Long articleId,
                                     HttpSession session) {
        User user = (User)session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);

        Favorite favorite = favoriteService.find(user, articleId);
        if (favorite != null) {// 用户收藏
            favoriteService.delete(user, articleId);
        } else {// 用户未收藏
            favoriteService.insert(user, articleId);
        }
        return ResultDTO.okof();
    }
}
