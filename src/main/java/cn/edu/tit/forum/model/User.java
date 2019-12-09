package cn.edu.tit.forum.model;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/8
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
