package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/13
 */
@Data
public class QQUser {
    private String openId;// 唯一标识
    private String nickname;// 用户在QQ空间的昵称。
    private String figureurl_qq_1;// 大小为40×40像素的QQ头像URL。
    private String gender;// 性别。 如果获取不到则默认返回"男"
}
