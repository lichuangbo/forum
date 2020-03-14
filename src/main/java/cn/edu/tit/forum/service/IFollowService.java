package cn.edu.tit.forum.service;

import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.User;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/3
 */
public interface IFollowService {
    void delete(User user, Long respUserId);

    void insert(User user, Long respUserId);

    Follow find(User user, Long recommendUserId);
}
