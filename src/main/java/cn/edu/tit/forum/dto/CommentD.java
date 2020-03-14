package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/25
 */
@Data
public class CommentD {
    private Long id;
    private String content;

    private Long userId;
    private Long respUserId;
    private User user;
    private User respUser;
    private Integer likeCount;

    private Long gmtCreate;
    private CommentD topComment[];
    private boolean isLiked;
}
