package cn.edu.tit.forum.advice;

import cn.edu.tit.forum.dto.ResultDTO;
import com.alibaba.fastjson.JSON;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/11
 */
@ControllerAdvice
@Slf4j
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Throwable ex,
                                           Model model) {
        String contentType = request.getContentType();
        // JSON格式请求---返回JSON
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            // if期待返回JSON，else期待返回modelAndView，两者不能共存。使用传统的servlet方式返回json
            try {
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                response.setContentType("application/json");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            } catch (IOException ioe){
                log.error("handle SpringBoot Exception try response error, {}", ioe);
            }
            return null;
        } else {// 页面请求----text/html，返回错误页面
            // 如果接收到的异常是自定义异常类型，将其自定义异常message放置到model域，以便页面显示
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
