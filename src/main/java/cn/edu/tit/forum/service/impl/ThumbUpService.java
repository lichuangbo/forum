package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.enums.NotifyStatusEnum;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.mapper.*;
import cn.edu.tit.forum.model.*;
import cn.edu.tit.forum.service.IThumbUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/29
 */
@Service
public class ThumbUpService implements IThumbUpService {

    @Autowired
    private ThumbUpMapper thumbUpMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private CommentMapper commentMapper;

    // 添加点赞记录
    @Override
    public int insert(ThumbUp thumbUp) {
        return thumbUpMapper.insert(thumbUp);
    }

    // 删除点赞记录
    @Override
    public int delete(ThumbUp thumbUp, Integer type) {
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andTargetIdEqualTo(thumbUp.getTargetId())
                .andUserIdEqualTo(thumbUp.getUserId())
                .andTypeEqualTo(type);
        return thumbUpMapper.deleteByExample(example);
    }

    // 查询是否点赞
    @Override
    public ThumbUp find(Long userId, Long targetId, Integer type) {
        ThumbUpExample example = new ThumbUpExample();
        example.createCriteria()
                .andTargetIdEqualTo(targetId)
                .andUserIdEqualTo(userId)
                .andTypeEqualTo(type);
        List<ThumbUp> thumbs = thumbUpMapper.selectByExample(example);
        if (thumbs == null || thumbs.size() == 0) {// 没有点赞
            return null;
        } else {// 用户点赞
            return thumbs.get(0);
        }
    }
}
