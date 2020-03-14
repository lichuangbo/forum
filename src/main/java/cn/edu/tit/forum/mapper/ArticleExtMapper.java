package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleExtMapper {
    // 增加浏览数
    int incView(Article record);

    // 增加文章表评论数
    int incCommentCount(Article record);

    // 文章推荐
    List<Article> selectRelative(Article article);

    // 其他文章
    List<Article> selectOther(Article article);

    // 根据输入关键字和标签检索
    List<Article> selectBySearchAndTag(@Param("tag") String tag, @Param("search") String search);

    // 点赞
    int incLikeCount(Article article);

    int decLikeCount(Article article);
}