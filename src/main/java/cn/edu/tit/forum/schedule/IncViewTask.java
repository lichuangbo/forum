package cn.edu.tit.forum.schedule;

import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/14
 */
@Component
public class IncViewTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Integer> hashOps;

    @Autowired
    private ArticleMapper articleMapper;

    @Scheduled(fixedRate = 1000 * 60 * 10)// 10分钟更新一次浏览量
    public void addViewCount() {
        String k_viewCount = KeyUtil.ARTICLE_VIEW_COUNT;

        Cursor<Map.Entry<String, Integer>> cursor_count = hashOps.scan(k_viewCount, ScanOptions.NONE);
        while (cursor_count.hasNext()) {
            Map.Entry<String, Integer> map = cursor_count.next();
            String key = map.getKey();
            Integer value = map.getValue();
            System.out.println("从redis中搬移数据，article表");
            Article newArticle = new Article();
            newArticle.setId(Long.parseLong(key));
            newArticle.setViewCount(value);
            articleMapper.updateByPrimaryKeySelective(newArticle);

            //从Redis中删除这条记录
            hashOps.delete(k_viewCount, key);
        }
    }
}
