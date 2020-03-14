package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.dto.CommentA;
import cn.edu.tit.forum.dto.CommentD;
import cn.edu.tit.forum.enums.CommentTypeEnum;
import cn.edu.tit.forum.enums.NotifyStatusEnum;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.enums.ThumbUpTypeEnum;
import cn.edu.tit.forum.exception.CustomizeErrorCode;
import cn.edu.tit.forum.exception.CustomizeException;
import cn.edu.tit.forum.mapper.*;
import cn.edu.tit.forum.model.*;
import cn.edu.tit.forum.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/12
 */
@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private ThumbUpService thumbUpService;

    @Override
    public CommentA listByArticleId(Long articleId,
                                    User user) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(articleId).andTypeEqualTo(1);
        commentExample.setOrderByClause("gmt_create desc");
        // 拿到根评论列表
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments == null || comments.size() == 0) {
            return null;
        }

        CommentA commentA = new CommentA();
        commentA.setCount(comments.size());
        CommentD[] commentDs = new CommentD[comments.size()];
        // 遍历根评论
        int i = 0;
        for (Comment comment : comments) {
            CommentD commentD = new CommentD();
            commentD.setId(comment.getId());
            commentD.setContent(comment.getContent());
            commentD.setUserId(comment.getUserId());
            UserExample example1 = new UserExample();
            example1.createCriteria().andIdEqualTo(comment.getUserId());
            List<User> users1 = userMapper.selectByExample(example1);
            commentD.setUser(users1.get(0));
            // 根评论respuserid是文章作者
            Article article = articleMapper.selectByPrimaryKey(articleId);
            UserExample example2 = new UserExample();
            example2.createCriteria().andIdEqualTo(article.getAuthorId());
            List<User> users2 = userMapper.selectByExample(example2);
            commentD.setRespUserId(article.getAuthorId());
            commentD.setRespUser(users2.get(0));
            commentD.setLikeCount(comment.getLikeCount());
            commentD.setGmtCreate(comment.getGmtCreate());
            Long userId = 0L;
            if (user != null) {
                userId = user.getId();
            }
            ThumbUp thumbUp = thumbUpService.find(userId, comment.getId(), ThumbUpTypeEnum.COMMENT.getType());
            if (thumbUp != null) {
                commentD.setLiked(true);
            } else {
                commentD.setLiked(false);
            }

            // 一级回复列表
            CommentExample commentExample1 = new CommentExample();
            commentExample1.createCriteria().andParentIdEqualTo(comment.getId()).andTypeEqualTo(2);
            List<Comment> comments1 = commentMapper.selectByExample(commentExample1);
            CommentD []commentDs2 = new CommentD[comments1.size()];
            // 遍历一级回复
            int j = 0;
            for (Comment comment1 : comments1) {
                CommentD commentD1 = new CommentD();
                commentD1.setId(comment1.getId());
                commentD1.setContent(comment1.getContent());
                commentD1.setUserId(comment1.getUserId());
                UserExample example3 = new UserExample();
                example3.createCriteria().andIdEqualTo(comment1.getUserId());
                List<User> users3 = userMapper.selectByExample(example3);
                commentD1.setUser(users3.get(0));
                // 一级回复的respUserId是根评论人
                commentD1.setRespUserId(comment1.getRespUserId());
                UserExample example4 = new UserExample();
                example4.createCriteria().andIdEqualTo(comment1.getRespUserId());
                List<User> users4 = userMapper.selectByExample(example4);
                commentD1.setRespUser(users4.get(0));
                commentD1.setLikeCount(comment1.getLikeCount());
                commentD1.setGmtCreate(comment.getGmtCreate());
                ThumbUp thumbUp1 = thumbUpService.find(userId, comment1.getId(), ThumbUpTypeEnum.COMMENT.getType());
                if (thumbUp1 != null) {
                    commentD1.setLiked(true);
                } else {
                    commentD1.setLiked(false);
                }
                // 二级回复
                commentD1.setTopComment(null);
                commentDs2[j++] = commentD1;
            }
            // 添加一级回复信息
            commentD.setTopComment(commentDs2);

            commentDs[i++] = commentD;
        }
        commentA.setComments(commentDs);
        return commentA;
    }

    @Transactional
    @Override
    public void insertComment(Comment comment, User user) {
        // 未选中问题评论
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        // 评论类型错误异常
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {// 回复
            // 回复的评论找不到异常
            Comment tempComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (tempComment == null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);

            // 通知功能-回复的问题找不到异常
            while(tempComment.getType() != 1) {
                tempComment = commentMapper.selectByPrimaryKey(tempComment.getParentId());
            }
            Article article = articleMapper.selectByPrimaryKey(tempComment.getParentId());
            if (article == null) throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);

            commentMapper.insert(comment);
            // 增加评论回复数
            Comment replyComment = new Comment();
            replyComment.setId(comment.getParentId());
            replyComment.setCommentCount(1);
            commentExtMapper.incCommentCount(replyComment);
            // 二级评论显示一级评论的content
            String content = commentMapper.selectByPrimaryKey(comment.getParentId()).getContent();
            // 添加通知
            createNotify(comment, comment.getRespUserId(), user, content, NotifyTypeEnum.REPLY_COMMENT, article);
        } else if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {// 评论
            Article article = articleMapper.selectByPrimaryKey(comment.getParentId());
            if (article == null) throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);

            commentMapper.insert(comment);
            // 增加文章表评论数
            article.setCommentCount(1);
            articleExtMapper.incCommentCount(article);
            // 添加通知
            createNotify(comment, article.getAuthorId(), user, article.getTitle(), NotifyTypeEnum.REPLY_ARTICLE, article);
        }
    }

    private void createNotify(Comment comment, Long respUserId, User user, String notifyTitle, NotifyTypeEnum notifyTypeEnum, Article article) {
        // 自己评论自己不用通知
        if (respUserId == comment.getUserId()) {
            return;
        }
        Notify notify = new Notify();
        notify.setNotifier(comment.getUserId());
        notify.setReceiver(respUserId);
        notify.setOuterId(article.getId());
        notify.setType(notifyTypeEnum.getType());
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        notify.setNotifierName(user.getNickname());
        notify.setOuterTitle(notifyTitle);
        notifyMapper.insert(notify);
    }

    // 减少点赞数
    public void decLikeCount(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setLikeCount(1);
        commentExtMapper.decLikeCount(comment);
    }

    // 增加点赞数
    public void incLikeCount(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setLikeCount(1);
        commentExtMapper.incLikeCount(comment);
    }

    // 检索点赞数
    public Integer findLikeCount(Long id) {
        return commentMapper.selectByPrimaryKey(id).getLikeCount();
    }
}
