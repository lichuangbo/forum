package cn.edu.tit.forum.model;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/8
 */
@Data
public class Question {
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
}