package cn.edu.tit.forum.model;

public class Notify {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.notifier
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long notifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.receiver
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long receiver;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.outer_id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long outerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.type
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.gmt_create
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.status
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.notifier_name
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private String notifierName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notify.outer_title
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    private String outerTitle;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.id
     *
     * @return the value of notify.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.id
     *
     * @param id the value for notify.id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.notifier
     *
     * @return the value of notify.notifier
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getNotifier() {
        return notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.notifier
     *
     * @param notifier the value for notify.notifier
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.receiver
     *
     * @return the value of notify.receiver
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.receiver
     *
     * @param receiver the value for notify.receiver
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.outer_id
     *
     * @return the value of notify.outer_id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getOuterId() {
        return outerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.outer_id
     *
     * @param outerId the value for notify.outer_id
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setOuterId(Long outerId) {
        this.outerId = outerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.type
     *
     * @return the value of notify.type
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.type
     *
     * @param type the value for notify.type
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.gmt_create
     *
     * @return the value of notify.gmt_create
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.gmt_create
     *
     * @param gmtCreate the value for notify.gmt_create
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.status
     *
     * @return the value of notify.status
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.status
     *
     * @param status the value for notify.status
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.notifier_name
     *
     * @return the value of notify.notifier_name
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public String getNotifierName() {
        return notifierName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.notifier_name
     *
     * @param notifierName the value for notify.notifier_name
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName == null ? null : notifierName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notify.outer_title
     *
     * @return the value of notify.outer_title
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public String getOuterTitle() {
        return outerTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notify.outer_title
     *
     * @param outerTitle the value for notify.outer_title
     *
     * @mbg.generated Sun Dec 29 15:48:58 AWST 2019
     */
    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }
}