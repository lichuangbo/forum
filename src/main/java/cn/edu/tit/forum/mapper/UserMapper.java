package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/8
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modified, bio, avatar_url) " +
            "values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{bio}, #{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creater);

    @Select("select * from user where account_id = #{accountId}")
    User finByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl}, bio = #{bio} where id=#{id}")
    void update(User dbUser);
}
