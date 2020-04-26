package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.Follow;
import cn.edu.tit.forum.model.FollowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FollowMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    long countByExample(FollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int deleteByExample(FollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int insert(Follow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int insertSelective(Follow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    List<Follow> selectByExampleWithRowbounds(FollowExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    List<Follow> selectByExample(FollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    Follow selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int updateByExampleSelective(@Param("record") Follow record, @Param("example") FollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int updateByExample(@Param("record") Follow record, @Param("example") FollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int updateByPrimaryKeySelective(Follow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbg.generated Sat Apr 25 22:13:37 AWST 2020
     */
    int updateByPrimaryKey(Follow record);
}