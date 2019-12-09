package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

/**
 * 查询工具，封装一对一关系
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/9
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creater;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

    private User user;
}
