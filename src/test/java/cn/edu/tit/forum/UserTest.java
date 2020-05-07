package cn.edu.tit.forum;

import cn.edu.tit.forum.dto.ArticleDTO;
import cn.edu.tit.forum.mapper.ArticleExtMapper;
import cn.edu.tit.forum.model.Article;
import cn.edu.tit.forum.service.impl.ArticleService;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/5/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumApplication.class)
public class UserTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Test
    public void testPageList() {
        PageInfo<ArticleDTO> articlePageInfo = articleService.listByUser(1, 10, 22L);
        Assert.assertNotNull(articlePageInfo.getList());
    }

    @Test
    public void testDecLike() {
        Article article = new Article();
        article.setId(62L);
        article.setLikeCount(1);
        int i = articleExtMapper.decLikeCount(article);
        Assert.assertEquals(1, i);
    }

    @Test
    public void test() {
        List<Article> articles = articleExtMapper.selectBySearchAndTag("", "");
        Assert.assertNotNull(articles);
    }
}
