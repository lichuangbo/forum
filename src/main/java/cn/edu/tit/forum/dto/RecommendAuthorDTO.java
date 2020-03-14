package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

import java.io.InputStream;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/5
 */
@Data
public class RecommendAuthorDTO {
    private User user;
    private Integer totalLikeCount;
    private Integer totalViewCount;
}
