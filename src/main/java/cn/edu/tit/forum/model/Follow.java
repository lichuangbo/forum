package cn.edu.tit.forum.model;

public class Follow {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column follow.id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column follow.user_id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column follow.follow_user_id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    private Long followUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column follow.gmt_create
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    private Long gmtCreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column follow.id
     *
     * @return the value of follow.id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column follow.id
     *
     * @param id the value for follow.id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column follow.user_id
     *
     * @return the value of follow.user_id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column follow.user_id
     *
     * @param userId the value for follow.user_id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column follow.follow_user_id
     *
     * @return the value of follow.follow_user_id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public Long getFollowUserId() {
        return followUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column follow.follow_user_id
     *
     * @param followUserId the value for follow.follow_user_id
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column follow.gmt_create
     *
     * @return the value of follow.gmt_create
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column follow.gmt_create
     *
     * @param gmtCreate the value for follow.gmt_create
     *
     * @mbg.generated Mon Apr 27 07:44:08 AWST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}