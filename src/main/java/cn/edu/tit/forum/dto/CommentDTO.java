package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

/**
 * 二级评论dto封装
 *
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/13
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;

    private User user;
    // 评论是否被点赞
    private Integer click;
}
