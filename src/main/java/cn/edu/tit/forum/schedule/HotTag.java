package cn.edu.tit.forum.schedule;

import cn.edu.tit.forum.cache.HotTagCache;
import cn.edu.tit.forum.mapper.QuestionMapper;
import cn.edu.tit.forum.model.Question;
import cn.edu.tit.forum.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/22
 * 方法一：遍历每一个问题，拿到他所有的标签，在对应的标签上+1,遍历完后统计所有标签的数量，就拿到了最热门的标签
 * 方法二：
 */
@Component
@Slf4j
public class HotTag {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0 1 * * *") // 凌晨1点执行
    public void hotTagSchedule() {
        /**
         * 优先级权重=问题数*3+评论数+浏览数
         */
        int offset = 0;
        int limit = 10;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        // 按页数遍历
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                // 获取问题的所有标签
                String[] tags = question.getTag().split(",");
                // 遍历每一个标签，根据标签获取 标签权重。然后循环计算标签总权重
                for (String tag : tags) {
                    Integer weight = priorities.get(tag);
                    if (weight != null) {
                        priorities.put(tag, weight + 3 + question.getCommentCount() + question.getViewCount());
                    } else {
                        priorities.put(tag, 3 + question.getCommentCount() + question.getViewCount());
                    }
                }
            }
            offset += limit;
        }
        // 将map放入对象
        hotTagCache.setTags(priorities);
        hotTagCache.updateTags(priorities);
    }
}
