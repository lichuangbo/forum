package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.mapper.FollowMapper;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.FollowExample;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/3
 */
@Service
public class FollowService implements IFollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public void delete(User user, Long respUserId) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(user.getId()).
                andFollowUserIdEqualTo(respUserId);
        followMapper.deleteByExample(followExample);
    }

    @Override
    public void insert(User user, Long respUserId) {
        Follow follow = new Follow();
        follow.setUserId(user.getId());
        follow.setFollowUserId(respUserId);
        follow.setGmtCreate(System.currentTimeMillis());
        followMapper.insert(follow);
    }

    @Override
    public Follow find(User user, Long respUserId) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(user.getId())
                .andFollowUserIdEqualTo(respUserId);
        List<Follow> follows = followMapper.selectByExample(followExample);
        if (follows != null && follows.size() > 0) {
            return follows.get(0);
        } else {
            return null;
        }
    }
}