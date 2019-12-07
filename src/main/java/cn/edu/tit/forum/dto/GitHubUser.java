package cn.edu.tit.forum.dto;

/**
 * GitHub用户登录信息实体类
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/7
 */
public class GitHubUser {
    private String name;// github用户名
    private Long id;    // id,不会变可以唯一标识一个用户
    private String bio; // github的个人描述

    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
