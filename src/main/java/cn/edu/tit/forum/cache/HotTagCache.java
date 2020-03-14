package cn.edu.tit.forum.cache;

import cn.edu.tit.forum.mapper.ArticleMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.model.ArticleExample;
import cn.edu.tit.forum.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/2
 */
@Repository
public class HotTagCache {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOp;

    public List<String> getHotTag() {
        if (redisTemplate.hasKey(KeyUtil.TAG_WEIGHT)) {
            // 计算前先清空
            zSetOp.removeRange(KeyUtil.TAG_WEIGHT, 0, -1);
        }

        List<Article> articles = articleMapper.selectByExample(new ArticleExample());
        for (Article article : articles) {
            String[] tags = article.getTag().split(",");
            for (String tag : tags) {
                // 遍历每一个标签，根据标签获取 对应标签的权重。如果为null，保存默认值;不为null，追加权重
                Double score = zSetOp.score(KeyUtil.TAG_WEIGHT, tag);
                if (score == null) {
                    // 之前没有该标签的文章，现在置为1
                    zSetOp.add(KeyUtil.TAG_WEIGHT, tag, 1);
                } else {
                    // 有该标签，在原基础上+1
                    zSetOp.incrementScore(KeyUtil.TAG_WEIGHT, tag, 1d);
                }
            }
        }
        Set<String> res = zSetOp.reverseRange(KeyUtil.TAG_WEIGHT, 0, 11);
        return res == null ? null : new ArrayList<>(res);
    }
}
