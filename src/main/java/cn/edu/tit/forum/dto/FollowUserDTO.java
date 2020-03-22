package cn.edu.tit.forum.dto;

import cn.edu.tit.forum.model.User;
import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/21
 */
@Data
public class FollowUserDTO {
    private User user;
    private boolean followed;
}
