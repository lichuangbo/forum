package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

@Data
public class ArticleDTO {
    // 首页点击id跳转文章详情
    private Long id;
    // 单个列表项
    private String title;
    private String content;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private Long gmtModified;
    private Long gmtCreate;
    // 二次编辑时，当前用户和文章作者判断
    private Long authorId;
    private String tag;
    // 用户信息
    private User user;
}
