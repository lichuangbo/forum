package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/8
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified, creater, tag) " +
            "values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creater}, #{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
