package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.RecommendAuthorDTO;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.mapper.FollowMapper;
import cn.edu.tit.forum.mapper.UserExtMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.*;
import cn.edu.tit.forum.service.IUserService;
import cn.edu.tit.forum.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/10
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private FollowMapper followMapper;

    public void createOrUpdate(User user) {
        // 用户登录，先判断用户是否存在
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdentifierEqualTo(user.getIdentifier());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            user.setFollowCount(0);
            userMapper.insert(user);
        } else {
            User dbUser = users.get(0);

            // 更新体
            User updateUser = new User();
            updateUser.setGmtModify(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setNickname(user.getNickname());
            updateUser.setDescript(user.getDescript());
            updateUser.setCredential(user.getCredential());
            updateUser.setFollowCount(user.getFollowCount());

            // 更新条件
            UserExample updateUserExample = new UserExample();
            updateUserExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, updateUserExample);
        }
    }

    @Override
    public void incFollowCount(User user) {
        userExtMapper.incFollowCount(user);
    }

    @Override
    public void decFollowCount(User user) {
        userExtMapper.decFollowCount(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RecommendAuthorDTO> recommendAuthor(User user) {
        List<User> users = userMapper.selectByExample(new UserExample());
        List<RecommendAuthorDTO> recommendAuthors = new ArrayList<>();
        for (User dbUser : users) {
            RecommendAuthorDTO recommendAuthor = new RecommendAuthorDTO();
            if (user.getId() != null) {
                // 只推荐未关注的
                FollowExample followExample = new FollowExample();
                followExample.createCriteria().andUserIdEqualTo(user.getId()).andFollowUserIdEqualTo(dbUser.getId());
                List<Follow> follows = followMapper.selectByExample(followExample);
                if (follows != null && follows.size() > 0) {
                    continue;
                }
            }
            recommendAuthor.setUser(dbUser);
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria().andAuthorIdEqualTo(dbUser.getId());
            List<Article> articles = articleMapper.selectByExample(articleExample);
            int totalLikeCount = 0, totalViewCount = 0;
            if (articles != null && articles.size() > 0) {
                for (Article article : articles) {
                    totalLikeCount += article.getLikeCount();
                    totalViewCount += article.getViewCount();
                }
            }
            recommendAuthor.setTotalLikeCount(totalLikeCount);
            recommendAuthor.setTotalViewCount(totalViewCount);

            recommendAuthors.add(recommendAuthor);
        }
        RandomUtil<RecommendAuthorDTO> randomUtil = new RandomUtil<>(recommendAuthors);
        List<RecommendAuthorDTO> randomList = randomUtil.getRandomList(recommendAuthors, 6);
        List<RecommendAuthorDTO> collect = randomList.stream()
                .filter(recommendAuthorDTO -> !recommendAuthorDTO.getUser().getId().equals(user.getId()))
                .sorted((a, b) -> b.getTotalLikeCount() - a.getTotalLikeCount())
                .limit(5)
                .collect(Collectors.toList());
        return collect;
    }
}
