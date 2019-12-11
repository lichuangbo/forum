package cn.edu.tit.forum.service;

import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/10
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        // 用户登录，先判断用户是否存在
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            User dbUser = users.get(0);

            // 更新体
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setBio(user.getBio());
            updateUser.setToken(user.getToken());

            // 更新条件
            UserExample updateUserExample = new UserExample();
            updateUserExample.createCriteria()
                    .andIdEqualTo(dbUser.getId());

            userMapper.updateByExampleSelective(updateUser, updateUserExample);
        }
    }
}
