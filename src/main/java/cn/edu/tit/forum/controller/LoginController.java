package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.RegisterDTO;
import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.model.UserExample;
import cn.edu.tit.forum.utils.MD5Util;
import cn.edu.tit.forum.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/4
 */
@Controller
public class LoginController {
    private String code = null;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String sign_in() {
        return "login/login";
    }

    @GetMapping("/register")
    public String sign_out() {
        return "login/register";
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public ResultDTO checkEmail(@RequestParam("email") String email) {
        UserExample example = new UserExample();
        example.createCriteria().andIdentifierEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            return ResultDTO.errorOf(CustomizeErrorCode.EMAIL_PRESENT);
        } else {
            return null;
        }
    }

    @PostMapping("/getCode")
    @ResponseBody
    public void getCode(@RequestParam("email") String email) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String date = dateFormat.format(new Date());
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        String text = "亲爱的" + email +
                "：<br/> &nbsp;&nbsp;&nbsp;您好！您正在进行邮箱验证，本次请求的验证码为：<br/><span style='color: #FF9900;font-size: 22px'>"
                + sb.toString() + "</span>(为了保障您账户的安全性，请在5分钟内完成验证)</br></br>&nbsp;&nbsp;&nbsp;<div><br/>" + date + "</div>";
        MailUtil.sendMail(email, text, "【星月论坛】邮箱验证码，Please verify your device");
        code = sb.toString();
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultDTO register(@RequestBody RegisterDTO registerDTO,
                              HttpServletResponse response) {
        String code = registerDTO.getCode().trim();
        if (code.equalsIgnoreCase(this.code)) {
            User user = new User();
            int id = (int) Math.round(Math.random() * (999999 - 100000) + 100000);
            String token = UUID.randomUUID().toString();
            user.setNickname("用户" + id);
            String password = MD5Util.encode2hex(registerDTO.getPassword());
            user.setEmailPassword(password);
            user.setCredential(token);
            user.setDescript("暂无描述");
            user.setFollowCount(0);
            user.setEmail("暂无");
            user.setIdentifyType("email");
            user.setAvatarUrl("https://cn-edu-tit-forum.obs.cn-north-4.myhuaweicloud.com:443/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.png");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            user.setIdentifier(registerDTO.getEmail());
            int flag = userMapper.insert(user);
            if (flag == 1) {
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(cookie);
                return ResultDTO.okof();
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.REGISTER_FAIL);
            }
        } else {
            return ResultDTO.errorOf(CustomizeErrorCode.INVALID_CODE);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultDTO login(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("state") boolean state,
                           HttpServletResponse response,
                           HttpSession session) {
        String password_md5 = MD5Util.encode2hex(password.trim());
        UserExample example = new UserExample();
        example.createCriteria().andIdentifierEqualTo(email).andEmailPasswordEqualTo(password_md5);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            String token = UUID.randomUUID().toString();
            User user = new User();
            user.setCredential(token);
            UserExample updateExample = new UserExample();
            updateExample.createCriteria().andIdentifierEqualTo(users.get(0).getIdentifier());
            userMapper.updateByExampleSelective(user, updateExample);
            // 持久化登录态
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(60 * 60 * 24 * 3);
            response.addCookie(tokenCookie);

            session.setAttribute("state", state);
            if (state) {// 点击记住密码，添加session信息
                session.setAttribute("email", email);
                session.setAttribute("password", password);
            } else {// 没有点击，让session失效
                session.setAttribute("email", null);
                session.setAttribute("password", null);
            }
            return ResultDTO.okof(users.get(0));
        } else {
            return ResultDTO.errorOf(CustomizeErrorCode.LOGIN_FAIL);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletResponse response) {
        session.removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
