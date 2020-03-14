package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/8
 */
@Data
public class WeiboUser {
    private String idstr;// 字符串型的用户UID
    private String screen_name;//用户昵称
    private String description;//用户个人描述
    private String avatar_hd;//用户头像地址（高清），高清头像原图
}
