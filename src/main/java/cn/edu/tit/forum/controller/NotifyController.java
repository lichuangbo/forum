package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.NotifyDTO;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/16
 */
@Controller
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/notify/{id}")
    public String notify(HttpServletRequest request,
                       @PathVariable(name = "id") Long id) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotifyDTO notifyDTO = notifyService.read(id, user);
        if (NotifyTypeEnum.REPLY_COMMENT.getType() == notifyDTO.getType() ||
            NotifyTypeEnum.REPLY_QUESTION.getType() == notifyDTO.getType() ||
            NotifyTypeEnum.LIKE_QUESTION.getType() == notifyDTO.getType() ||
            NotifyTypeEnum.LIKE_COMMENT.getType() == notifyDTO.getType()) {
            return "redirect:/question/" + notifyDTO.getOuterId();
        }
        return "redirect:/";
    }
}
