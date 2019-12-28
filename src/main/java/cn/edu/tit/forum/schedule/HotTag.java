package cn.edu.tit.forum.schedule;

import cn.edu.tit.forum.cache.HotTagCache;
import cn.edu.tit.forum.dto.TagAttributes;
import cn.edu.tit.forum.mapper.QuestionMapper;
import cn.edu.tit.forum.model.Question;
import cn.edu.tit.forum.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/22
 */
@Component
@Slf4j
public class HotTag {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 6)// 每六个小时更新一次
    public void hotTagSchedule() {
        /**
         * 优先级权重=问题数*3+评论数+浏览数
         */
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());
        Map<String, TagAttributes> weights = new HashMap<>();
        for (Question question : questions) {
            // 获取问题的所有标签
            String[] tags = question.getTag().split(",");
            // 遍历每一个标签，根据标签获取 对应标签权重。然后追加权重
            for (String tag : tags) {
                TagAttributes tagAttributes = weights.get(tag);
                if (tagAttributes != null) {
                    tagAttributes.setCommentCount(tagAttributes.getCommentCount() + question.getCommentCount());
                    tagAttributes.setViewCount(tagAttributes.getViewCount() + question.getViewCount());
                    tagAttributes.setQuestionCount(tagAttributes.getQuestionCount() + 1);
                    tagAttributes.setWeight(tagAttributes.getWeight() + 3 + question.getCommentCount() + question.getViewCount());
                    weights.put(tag, tagAttributes);
                } else {
                    TagAttributes temp = new TagAttributes();
                    temp.setCommentCount(question.getCommentCount());
                    temp.setViewCount(question.getViewCount());
                    temp.setQuestionCount(1);
                    temp.setWeight(3 + question.getCommentCount() + question.getViewCount());
                    weights.put(tag, temp);
                }
            }
        }
        // 将map放入对象
        hotTagCache.setTags(weights);
        hotTagCache.updateTags(weights);
    }
}
