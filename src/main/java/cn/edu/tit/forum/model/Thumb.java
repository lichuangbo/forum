package cn.edu.tit.forum.model;

public class Thumb {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column thumb.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column thumb.userid
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column thumb.questionid
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long questionid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column thumb.id
     *
     * @return the value of thumb.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column thumb.id
     *
     * @param id the value for thumb.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column thumb.userid
     *
     * @return the value of thumb.userid
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column thumb.userid
     *
     * @param userid the value for thumb.userid
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column thumb.questionid
     *
     * @return the value of thumb.questionid
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getQuestionid() {
        return questionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column thumb.questionid
     *
     * @param questionid the value for thumb.questionid
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }
}