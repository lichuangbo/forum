package cn.edu.tit.forum.service;

import cn.edu.tit.forum.model.ThumbUp;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/1
 */
public interface IThumbUpService {
    int insert(ThumbUp thumbUp);

    int delete(ThumbUp thumbUp, Integer type);

    ThumbUp find(Long userId, Long targetId, Integer type);
}
