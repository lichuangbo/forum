package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.User;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/3
 */
public interface UserExtMapper {
    void incFollowCount(User user);

    void decFollowCount(User user);
}
