package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.RecommendAuthorDTO;
import cn.edu.tit.forum.model.User;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/20
 */
public interface IUserService {
    void createOrUpdate(User user);

    void incFollowCount(User user);

    void decFollowCount(User user);

    User findById(Long id);

    List<RecommendAuthorDTO> recommendAuthor(User user);

    int updateUser(User user);
}
