package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/15
 */
@Data
public class RegisterDTO {
    private String email;
    private String password;
    private String code;
}
