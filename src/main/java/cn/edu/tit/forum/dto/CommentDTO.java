package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/13
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;

    private User user;
}
