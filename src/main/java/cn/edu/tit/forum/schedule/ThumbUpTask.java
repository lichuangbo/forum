package cn.edu.tit.forum.schedule;

import cn.edu.tit.forum.enums.ThumbUpTypeEnum;
import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.mapper.ThumbUpMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.ThumbUp;
import cn.edu.tit.forum.model.ThumbUpExample;
import cn.edu.tit.forum.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/8
 */
@Component
public class ThumbUpTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Integer> hashOps;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ThumbUpMapper thumbUpMapper;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)// 每三个小时持久化一次
//    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void thumbUp() {
        String k_status = KeyUtil.ARTICLE_Like_STATUS;
        String k_count = KeyUtil.ARTICLE_LIKE_COUNT;

        Cursor<Map.Entry<String, Integer>> cursor_status = hashOps.scan(k_status, ScanOptions.NONE);
        while (cursor_status.hasNext()) {
            Map.Entry<String, Integer> entry = cursor_status.next();
            String key = entry.getKey();
            // 分离出 likedUserId，likedArticleId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedArticleId = split[1];
            Integer value = entry.getValue();

            long userId = Long.parseLong(likedUserId);
            long articleId = Long.parseLong(likedArticleId);

            ThumbUpExample thumbUpExample = new ThumbUpExample();
            thumbUpExample.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andTargetIdEqualTo(articleId)
                    .andTypeEqualTo(ThumbUpTypeEnum.QUESTION.getType());
            System.out.println("开始搬移数据，thumb表");
            List<ThumbUp> thumbs = thumbUpMapper.selectByExample(thumbUpExample);
            System.out.println("key" + key + "=>" + value + "=>" + thumbs);
            if (thumbs != null && thumbs.size() > 0 && thumbs.get(0) != null && value == 0) {// 删除记录
                thumbUpMapper.deleteByExample(thumbUpExample);
            } else if (thumbs != null && thumbs.size() == 0 && value == 1) {
                ThumbUp insertThumbUp = new ThumbUp();
                insertThumbUp.setUserId(userId);
                insertThumbUp.setTargetId(articleId);
                insertThumbUp.setType(ThumbUpTypeEnum.QUESTION.getType());
                insertThumbUp.setGmtCreate(System.currentTimeMillis());
                thumbUpMapper.insert(insertThumbUp);
            }

            // 从 Redis 中删除
            hashOps.delete(k_status, key);
        }

        Cursor<Map.Entry<String, Integer>> cursor_count = hashOps.scan(k_count, ScanOptions.NONE);
        while (cursor_count.hasNext()) {
            Map.Entry<String, Integer> map = cursor_count.next();
            // 将点赞数量存储在 LikedCount
            String key = map.getKey();
            Integer value = map.getValue();
            System.out.println("从redis中搬移数据，article表");
            Article newArticle = new Article();
            newArticle.setId(Long.parseLong(key));
            newArticle.setLikeCount(value);
            articleMapper.updateByPrimaryKeySelective(newArticle);

            //从Redis中删除这条记录
            hashOps.delete(k_count, key);
        }
    }
}
