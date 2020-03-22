package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/22
 */
@Data
public class FollowMessageDTO {
    private String message;
    // 关注人数
    private Long followedCount;
    // 关注我的人数
    private Integer followerCount;
}
