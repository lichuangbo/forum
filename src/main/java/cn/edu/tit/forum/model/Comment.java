package cn.edu.tit.forum.model;

public class Comment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.parent_id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.type
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.commentor
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long commentor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.gmt_create
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.gmt_modified
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.like_count
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.content
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.comment_count
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Integer commentCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.id
     *
     * @return the value of comment.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.id
     *
     * @param id the value for comment.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.parent_id
     *
     * @return the value of comment.parent_id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.parent_id
     *
     * @param parentId the value for comment.parent_id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.type
     *
     * @return the value of comment.type
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.type
     *
     * @param type the value for comment.type
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.commentor
     *
     * @return the value of comment.commentor
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getCommentor() {
        return commentor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.commentor
     *
     * @param commentor the value for comment.commentor
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setCommentor(Long commentor) {
        this.commentor = commentor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.gmt_create
     *
     * @return the value of comment.gmt_create
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.gmt_create
     *
     * @param gmtCreate the value for comment.gmt_create
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.gmt_modified
     *
     * @return the value of comment.gmt_modified
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.gmt_modified
     *
     * @param gmtModified the value for comment.gmt_modified
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.like_count
     *
     * @return the value of comment.like_count
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.like_count
     *
     * @param likeCount the value for comment.like_count
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.content
     *
     * @return the value of comment.content
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.content
     *
     * @param content the value for comment.content
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.comment_count
     *
     * @return the value of comment.comment_count
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.comment_count
     *
     * @param commentCount the value for comment.comment_count
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}