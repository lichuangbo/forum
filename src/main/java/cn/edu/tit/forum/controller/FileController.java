package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.FileDTO;
import cn.edu.tit.forum.provider.OBSProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/16
 */
@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private OBSProvider obsProvider;

    @RequestMapping("/upload")
    @ResponseBody
    public FileDTO upload(MultipartHttpServletRequest multipartRequest) {
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String url = obsProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            return fileDTO;
        } catch (IOException e) {
            log.error("upload image in editormd error, {}", e);
        }
        return null;
    }

    @RequestMapping("/uploadAvatar")
    @ResponseBody
    public FileDTO upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = obsProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            return fileDTO;
        } catch (IOException e) {
            log.error("upload avatar image in editormd error, {}", e);
        }
        return null;
    }
}
