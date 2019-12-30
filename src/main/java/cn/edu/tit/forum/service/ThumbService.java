package cn.edu.tit.forum.service;

import cn.edu.tit.forum.mapper.ThumbMapper;
import cn.edu.tit.forum.model.Thumb;
import cn.edu.tit.forum.model.ThumbExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/29
 */
@Service
public class ThumbService {

    @Autowired
    private ThumbMapper thumbMapper;

    // 添加点赞记录
    public int insert(Thumb thumb) {
        return thumbMapper.insert(thumb);
    }

    // 删除点赞记录
    public int delete(Thumb thumb) {
        ThumbExample example = new ThumbExample();
        example.createCriteria()
                .andQuestionidEqualTo(thumb.getQuestionid())
                .andUseridEqualTo(thumb.getUserid());
        return thumbMapper.deleteByExample(example);
    }

    // 查询是否点赞
    public Thumb find(Long userId, Long questionId) {
        ThumbExample example = new ThumbExample();
        example.createCriteria()
                .andQuestionidEqualTo(questionId)
                .andUseridEqualTo(userId);
        List<Thumb> thumbs = thumbMapper.selectByExample(example);
        if (thumbs == null || thumbs.size() == 0) {// 没有点赞
            return null;
        } else {// 用户点赞
            return thumbs.get(0);
        }
    }
}
