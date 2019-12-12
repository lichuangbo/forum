package cn.edu.tit.forum.advice;

import cn.edu.tit.forum.dto.ResultDTO;
import com.alibaba.fastjson.JSON;
import exception.CustomizeErrorCode;
import exception.CustomizeException;
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
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Throwable ex,
                                           Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            // 返回JSON
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                response.setContentType("application/json");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            } catch (IOException ioe){
            }
            return null;
        } else {
            // 页面跳转
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
