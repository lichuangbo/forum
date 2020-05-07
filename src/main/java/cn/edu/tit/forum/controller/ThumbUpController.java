package cn.edu.tit.forum.controller;

import cn.edu.tit.forum.dto.ResultDTO;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.enums.ThumbUpTypeEnum;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.model.ThumbUp;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.impl.ArticleService;
import cn.edu.tit.forum.service.impl.CommentService;
import cn.edu.tit.forum.service.impl.NotifyService;
import cn.edu.tit.forum.service.impl.ThumbUpService;
import cn.edu.tit.forum.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/29
 */
@Controller
public class ThumbUpController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ThumbUpService thumbUpService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Integer> hashOps;

    @ResponseBody
    @Transactional
    @RequestMapping(value = "/thumbUpArticle", method = RequestMethod.POST)
    public ResultDTO thumbUpArticle(Long targetId,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);

        /*ThumbUp thumb = thumbUpService.find(user.getId(), targetId, ThumbUpTypeEnum.QUESTION.getType());
        if (thumb != null) {// 当前用户点击了当前文章
            // 问题表点赞数-1
            articleService.decLikeCount(targetId);
            // 点赞表删除对应记录
            ThumbUp deleteThumb = new ThumbUp();
            deleteThumb.setUserId(user.getId());
            deleteThumb.setTargetId(targetId);
            thumbUpService.delete(deleteThumb, ThumbUpTypeEnum.QUESTION.getType());
            // 取消通知
            notifyService.cancelArticleNotify(targetId, NotifyTypeEnum.LIKE_ARTICLE.getType());
        } else {// 当前用户没有点击当前文章
            // 问题表点赞数+1
            articleService.incLikeCount(targetId);
            // 点赞表添加对应记录
            ThumbUp addThumb = new ThumbUp();
            addThumb.setUserId(user.getId());
            addThumb.setTargetId(targetId);
            addThumb.setType(ThumbUpTypeEnum.QUESTION.getType());
            addThumb.setGmtCreate(System.currentTimeMillis());
            thumbUpService.insert(addThumb);
            // 通知被点赞用户
            notifyService.notifyRespArticleUser(user.getId(), targetId);
        }
        // 查询点赞数
        Integer likeCount = articleService.findLikeCount(targetId);
        return ResultDTO.okof(likeCount);*/

        String k_status = KeyUtil.ARTICLE_Like_STATUS;
        String k_count = KeyUtil.ARTICLE_LIKE_COUNT;
        String hk_status = KeyUtil.getHashArticleLikeStatus(user.getId(), targetId);
        String hk_count = KeyUtil.getHashArticleLikeCount(targetId);
        //  查询是否有该缓存key
        if (hashOps.hasKey(k_status, hk_status)) {// 有key,从缓存中查询
            Integer thumbUpStatus = hashOps.get(k_status, hk_status);
            if (thumbUpStatus != null && thumbUpStatus == 1) {// 点赞
                // count缓存-1
                hashOps.increment(k_count, hk_count, -1);
                // like缓存取消点赞状态
                hashOps.put(k_status, hk_status, 0);
            } else {// 未点赞
                // count缓存+1
                hashOps.increment(k_count, hk_count, 1);
                // like缓存保存点赞状态
                hashOps.put(k_status, hk_status, 1);
            }
        } else {// 没有key，从数据库中查询
            ThumbUp thumbUp = thumbUpService.find(user.getId(), targetId, ThumbUpTypeEnum.QUESTION.getType());
            Integer likeCount = articleService.findLikeCount(targetId);
            hashOps.put(k_count, hk_count, likeCount);
            if (thumbUp != null) {// 当前用户点击了当前文章
                // count缓存-1
                hashOps.increment(k_count, hk_count, -1);
                // like缓存取消点赞状态
                hashOps.put(k_status, hk_status, 0);
            } else {
                // count缓存+1
                hashOps.increment(k_count, hk_count, 1);
                // like缓存保存点赞状态
                hashOps.put(k_status, hk_status, 1);
                // 通知
                notifyService.notifyRespArticleUser(user.getId(), targetId);
            }
        }
        Integer num = hashOps.get(k_count, hk_count);
        return ResultDTO.okof(num);
    }

    @ResponseBody
    @Transactional
    @RequestMapping(value = "/thumbUpComment", method = RequestMethod.POST)
    public ResultDTO thumbUpComment(Long targetId,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);

        ThumbUp thumb = thumbUpService.find(user.getId(), targetId, ThumbUpTypeEnum.COMMENT.getType());
        if (thumb != null) {// 当前用户点击了评论
            // 评论表点赞数-1
            commentService.decLikeCount(targetId);
            // 点赞表删除对应记录
            ThumbUp deleteThumb = new ThumbUp();
            deleteThumb.setUserId(user.getId());
            deleteThumb.setTargetId(targetId);
            thumbUpService.delete(deleteThumb, ThumbUpTypeEnum.COMMENT.getType());
            // 取消通知
            notifyService.cancelCommentNotify(targetId, NotifyTypeEnum.LIKE_COMMENT.getType());
        } else {// 当前用户没有点击评论
            // 评论表点赞数+1
            commentService.incLikeCount(targetId);
            // 点赞表添加对应记录
            ThumbUp addThumb = new ThumbUp();
            addThumb.setUserId(user.getId());
            addThumb.setTargetId(targetId);
            addThumb.setType(ThumbUpTypeEnum.COMMENT.getType());
            addThumb.setGmtCreate(System.currentTimeMillis());
            thumbUpService.insert(addThumb);
            // 通知被点赞用户
            notifyService.notifyRespCommentUser(user.getId(), targetId);
        }
        // 查询点赞数
        Integer likeCount = commentService.findLikeCount(targetId);
        return ResultDTO.okof(likeCount);
    }
}
