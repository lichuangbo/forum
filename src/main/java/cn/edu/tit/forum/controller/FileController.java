package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/16
 */
@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload() {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("返回信息");
        fileDTO.setUrl("/images/coder.jpg");
        return fileDTO;
    }
}
