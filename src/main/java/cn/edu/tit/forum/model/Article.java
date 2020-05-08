package cn.edu.tit.forum.model;

public class Article {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.id
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.author_id
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Long authorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.title
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.content
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.tag
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.view_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Integer viewCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.like_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Integer likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.comment_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Integer commentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.del_flag
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private String delFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.gmt_create
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.gmt_modified
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    private Long gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.id
     *
     * @return the value of article.id
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.id
     *
     * @param id the value for article.id
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.author_id
     *
     * @return the value of article.author_id
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.author_id
     *
     * @param authorId the value for article.author_id
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.title
     *
     * @return the value of article.title
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.title
     *
     * @param title the value for article.title
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.content
     *
     * @return the value of article.content
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.content
     *
     * @param content the value for article.content
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.tag
     *
     * @return the value of article.tag
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.tag
     *
     * @param tag the value for article.tag
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.view_count
     *
     * @return the value of article.view_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.view_count
     *
     * @param viewCount the value for article.view_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.like_count
     *
     * @return the value of article.like_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.like_count
     *
     * @param likeCount the value for article.like_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.comment_count
     *
     * @return the value of article.comment_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.comment_count
     *
     * @param commentCount the value for article.comment_count
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.del_flag
     *
     * @return the value of article.del_flag
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.del_flag
     *
     * @param delFlag the value for article.del_flag
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.gmt_create
     *
     * @return the value of article.gmt_create
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.gmt_create
     *
     * @param gmtCreate the value for article.gmt_create
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.gmt_modified
     *
     * @return the value of article.gmt_modified
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.gmt_modified
     *
     * @param gmtModified the value for article.gmt_modified
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}