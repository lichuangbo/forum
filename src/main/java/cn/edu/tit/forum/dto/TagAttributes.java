package cn.edu.tit.forum.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/28
 */
@Data
public class TagAttributes {
    // 标签权重
    private Integer weight;
    // 标签问题数
    private Integer questionCount;
    // 标签评论数
    private Integer commentCount;
    // 标签浏览量
    private Integer viewCount;
}
