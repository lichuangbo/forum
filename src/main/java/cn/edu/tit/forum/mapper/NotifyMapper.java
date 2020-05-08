package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.Notify;
import cn.edu.tit.forum.model.NotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NotifyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    long countByExample(NotifyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int deleteByExample(NotifyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int insert(Notify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int insertSelective(Notify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    List<Notify> selectByExampleWithRowbounds(NotifyExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    List<Notify> selectByExample(NotifyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    Notify selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByExampleSelective(@Param("record") Notify record, @Param("example") NotifyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByExample(@Param("record") Notify record, @Param("example") NotifyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByPrimaryKeySelective(Notify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notify
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByPrimaryKey(Notify record);
}