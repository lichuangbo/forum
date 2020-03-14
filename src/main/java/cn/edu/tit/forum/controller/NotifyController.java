package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.NotifyDTO;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.model.Notify;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.NotifyService;
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
 * @created 2019/12/16
 */
@Controller
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/notify")
    public String toNotify(HttpSession session,
                           Model model,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        PageInfo<NotifyDTO> notifyDTOPageInfo = notifyService.queryList(user.getId(), page, size);
        notifyService.readNotify(user.getId());
        model.addAttribute("notifyDTOPageInfo", notifyDTOPageInfo);
        return "notify";
    }

    @GetMapping("/notify/{id}")
    public String notify(HttpSession session,
                         @PathVariable(name = "id") Long id) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        Notify notify = notifyService.read(id, user);
        if (NotifyTypeEnum.REPLY_COMMENT.getType() == notify.getType() ||
                NotifyTypeEnum.REPLY_ARTICLE.getType() == notify.getType() ||
                NotifyTypeEnum.LIKE_ARTICLE.getType() == notify.getType() ||
                NotifyTypeEnum.LIKE_COMMENT.getType() == notify.getType()) {
            return "redirect:/article/" + notify.getOuterId();
        }
        return "redirect:/";
    }
}
