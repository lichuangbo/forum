package cn.edu.tit.forum.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理SpringBoot没处理完的异常显示（如请求路径不对）
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
@Controller("/error")
@RequestMapping("${server.error.path:${error.path:/error}}")// 源码BasicErrorController中是这么写的
public class CustomizeErrorController implements ErrorController {
    // 返回错误页的路径
    @Override
    public String getErrorPath() {
        return "error";
    }

    // text/html格式
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()) {
            model.addAttribute("message", "你这个请求错了吧，要不然换个姿势？");
        }
        if (status.is5xxServerError()) {
            model.addAttribute("message", "服务器已经冒烟了，要不然你稍后再试试！！！");
        }

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
