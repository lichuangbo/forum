package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long gmtCreate;
    private Integer likeCount;
    private Integer commentCount;

    private User user;
    // 评论是否被点赞
    private boolean isLiked;
}
