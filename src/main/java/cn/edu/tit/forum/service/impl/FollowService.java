package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.FollowUserDTO;
import cn.edu.tit.forum.mapper.FollowMapper;
import cn.edu.tit.forum.mapper.UserMapper;
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
        if (user == null) {
            return null;
        }
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
    public PageInfo<FollowUserDTO> listByUser(int page, int size, Long userId, User sessionUser) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(userId);
        PageHelper.startPage(page, size);
        List<Follow> follows = followMapper.selectByExample(followExample);

        PageInfo<Follow> userPoPageInfo = new PageInfo<>(follows);
        PageInfo<FollowUserDTO> userVoPageInfo = PageUtil.PageInfo2PageInfoVo(userPoPageInfo);

        List<FollowUserDTO> followUserDTOS = new ArrayList<>();
        for (Follow follow : follows) {
            FollowUserDTO followUserDTO = new FollowUserDTO();
            User user = userMapper.selectByPrimaryKey(follow.getFollowUserId());
            followUserDTO.setUser(user);
            if (sessionUser != null) {
                // 当前用户是否关注列表中的这个人
                FollowExample followExample1 = new FollowExample();
                followExample1.createCriteria().andUserIdEqualTo(sessionUser.getId()).andFollowUserIdEqualTo(follow.getFollowUserId());
                List<Follow> follows1 = followMapper.selectByExample(followExample1);
                if (follows1 != null && follows1.size() > 0) {
                    followUserDTO.setFollowed(true);
                } else {
                    followUserDTO.setFollowed(false);
                }
            }

            followUserDTOS.add(followUserDTO);
        }
        for (FollowUserDTO followUserDTO : followUserDTOS) {
            userVoPageInfo.getList().add(followUserDTO);
        }
        return userVoPageInfo;
    }

    @Override
    public PageInfo<FollowUserDTO> listByTargetUser(int page, int size, Long userId, User sessionUser) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFollowUserIdEqualTo(userId);
        PageHelper.startPage(page, size);
        List<Follow> follows = followMapper.selectByExample(followExample);

        PageInfo<Follow> userPoPageInfo = new PageInfo<>(follows);
        PageInfo<FollowUserDTO> userVoPageInfo = PageUtil.PageInfo2PageInfoVo(userPoPageInfo);

        List<FollowUserDTO> followUserDTOS = new ArrayList<>();
        for (Follow follow : follows) {
            FollowUserDTO followUserDTO = new FollowUserDTO();
            User user = userMapper.selectByPrimaryKey(follow.getUserId());
            followUserDTO.setUser(user);
            if (sessionUser != null) {
                FollowExample followExample1 = new FollowExample();
                followExample1.createCriteria().andUserIdEqualTo(sessionUser.getId()).andFollowUserIdEqualTo(follow.getUserId());
                List<Follow> follows1 = followMapper.selectByExample(followExample1);
                if (follows1 != null && follows1.size() > 0) {
                    followUserDTO.setFollowed(true);
                } else {
                    followUserDTO.setFollowed(false);
                }
            }

            followUserDTOS.add(followUserDTO);
        }
        for (FollowUserDTO followUserDTO : followUserDTOS) {
            userVoPageInfo.getList().add(followUserDTO);
        }
        return userVoPageInfo;
    }
}