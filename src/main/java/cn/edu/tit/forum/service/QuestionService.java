package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.QuestionDTO;
import cn.edu.tit.forum.mapper.QuestionMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.Question;
import cn.edu.tit.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/9
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> queryList() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            // Spring提供的，快速封装查询体
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
