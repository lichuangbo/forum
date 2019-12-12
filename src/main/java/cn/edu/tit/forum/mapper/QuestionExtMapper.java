package cn.edu.tit.forum.mapper;

import cn.edu.tit.forum.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}