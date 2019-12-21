package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.dto.QuestionQueryDTO;
import cn.edu.tit.forum.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelative(Question question);

    int countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}