package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * GitHub用户登录信息实体类
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
@Data
public class GitHubUser {
    private String name;// github用户名
    private String id;    // id,不会变可以唯一标识一个用户
    private String bio; // github的个人描述
    private String avatarUrl;// 头像url
}
