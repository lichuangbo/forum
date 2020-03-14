package cn.edu.tit.forum;

import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.service.impl.ArticleService;
import cn.edu.tit.forum.service.impl.ThumbUpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumApplication.class)
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
//    private HashOperations<String, Long, Integer> hashOps;
    private HashOperations<String, String, Integer> hashOps;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ThumbUpService thumbUpService;

    @Test
    public void test2() {
//        if (hashOps.hasKey("question:like", 3L)) {
//            Integer redis_like = hashOps.get("question:like", 3L);
//            System.out.println("redis_like:" + redis_like);
//        } else {
//            Integer mysql_like = questionService.findLikeCount(3L);
//            System.out.println("mysql_like:" + mysql_like);
//            hashOps.put("question:like", 3L, mysql_like);
//        }
    }

    @Test
    public void test3() {
//        articleService.incLikeCount(3L);
//        hashOps.delete("question:like", 3L);
    }

    @Test
    public void test4() {
        // 存储点赞状态
//        hashOps.put("user:article", "1::3", 1);
        // 取消点赞状态
//        hashOps.put("user:article", "1::3", 0);
        // 获取点赞状态，可以作为页面展示
//        Integer status = hashOps.get("user:article", "1::3");
//        System.out.println(status);
        // 数据准备
//        hashOps.put("user:article", "1::4", 1);
//        hashOps.put("user:article", "2::3", 1);
//        hashOps.put("user:article", "2::4", 1);
        // 从redis中取出数据
        /*Cursor<Map.Entry<String, Integer>> cursor = hashOps.scan("user:article", ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<String, Integer> entry = cursor.next();
            String key = entry.getKey();
            // 分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedArticleId = split[1];
            Integer value = entry.getValue();
            System.out.println("用户" + likedUserId + "点赞了文章" + likedArticleId + "===" + value);

            Thumb thumb = new Thumb();
            thumb.setUserid(Long.parseLong(likedUserId));
            thumb.setTypeid(Long.parseLong(likedArticleId));
            thumb.setType(ThumbUpTypeEnum.QUESTION.getType());
            thumbService.insert(thumb);

            //存到 list 后从 Redis 中删除
            hashOps.delete("user:article", key);
        }*/

//        hashOps.put("question:like", "7", 0);
        // 增加点赞，question表对应likeCount+1, 先在redis中缓存下然后异步持久化到MySQL
//        hashOps.increment("question:like", "7", 1);
        // 减少点赞
//        hashOps.increment("question:like", "7", -1);
//        Integer num = hashOps.get("question:like", "7");
//        log.info("num:" + num);
        // 数据准备
//        hashOps.put("question:like", "5", 0);
//        hashOps.put("question:like", "6", 0);
//        hashOps.put("question:like", "7", 0);
//        hashOps.increment("question:like", "5", 1);
//        hashOps.increment("question:like", "6", -1);
//        hashOps.increment("question:like", "7", -2);
        Cursor<Map.Entry<String, Integer>> cursor = hashOps.scan("question::like", ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<String, Integer> map = cursor.next();
            // 将点赞数量存储在 LikedCount
            String key = map.getKey();
            Integer value = map.getValue();
            System.out.println("文章" + key + "likecount=" + value);

//            articleService.incLikeCount(Long.parseLong(key), value);

            //从Redis中删除这条记录
            hashOps.delete("question::like", key);
        }
    }
}
