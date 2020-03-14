package cn.edu.tit.forum.utils;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/22
 */
public class KeyUtil {
    public static final String TAG_WEIGHT = "tag::weight";

    public static final String ARTICLE_Like_STATUS = "user::article";

    public static final String ARTICLE_LIKE_COUNT = "article::likeCount";

    public static final String ARTICLE_VIEW_COUNT = "article::viewCount";

    public static String getHashArticleLikeStatus(Long userId, Long articleId) {
        return userId + "::" + articleId;
    }

    public static String getHashArticleLikeCount(Long articleId) {
        return articleId.toString();
    }

    public static String getHashArticleViewCount(Long articleId) {
        return articleId.toString();
    }
}
