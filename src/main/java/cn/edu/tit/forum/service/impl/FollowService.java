package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.mapper.FollowMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.FollowExample;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.IFollowService;
import cn.edu.tit.forum.utils.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public long countFollowUser(Long userId) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(userId);
        long result = followMapper.countByExample(followExample);
        return result;
    }

    @Override
    public PageInfo<User> listByUser(int page, int size, Long userId) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(userId);
        PageHelper.startPage(page, size);
        List<Follow> follows = followMapper.selectByExample(followExample);

        PageInfo<Follow> userPoPageInfo = new PageInfo<>(follows);
        PageInfo<User> userVoPageInfo = PageUtil.PageInfo2PageInfoVo(userPoPageInfo);
        List<User> users = new ArrayList<>();
        for (Follow follow : follows) {
            User user = userMapper.selectByPrimaryKey(follow.getFollowUserId());
            users.add(user);
        }
        for (User user : users) {
            userVoPageInfo.getList().add(user);
        }
        return userVoPageInfo;
    }

    @Override
    public PageInfo<User> listByTargetUser(int page, int size, Long userId) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFollowUserIdEqualTo(userId);
        PageHelper.startPage(page, size);
        List<Follow> follows = followMapper.selectByExample(followExample);

        PageInfo<Follow> userPoPageInfo = new PageInfo<>(follows);
        PageInfo<User> userVoPageInfo = PageUtil.PageInfo2PageInfoVo(userPoPageInfo);
        List<User> users = new ArrayList<>();
        for (Follow follow : follows) {
            User user = userMapper.selectByPrimaryKey(follow.getUserId());
            users.add(user);
        }
        for (User user : users) {
            userVoPageInfo.getList().add(user);
        }
        return userVoPageInfo;
    }
}