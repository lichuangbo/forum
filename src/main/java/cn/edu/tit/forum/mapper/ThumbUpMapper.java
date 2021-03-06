package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.ThumbUp;
import cn.edu.tit.forum.model.ThumbUpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ThumbUpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    long countByExample(ThumbUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int deleteByExample(ThumbUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int insert(ThumbUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int insertSelective(ThumbUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    List<ThumbUp> selectByExampleWithRowbounds(ThumbUpExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    List<ThumbUp> selectByExample(ThumbUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    ThumbUp selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByExampleSelective(@Param("record") ThumbUp record, @Param("example") ThumbUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByExample(@Param("record") ThumbUp record, @Param("example") ThumbUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByPrimaryKeySelective(ThumbUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table thumb_up
     *
     * @mbg.generated Fri May 08 22:58:41 AWST 2020
     */
    int updateByPrimaryKey(ThumbUp record);
}