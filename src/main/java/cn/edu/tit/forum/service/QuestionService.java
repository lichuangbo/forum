package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.PageNationDTO;
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

    public PageNationDTO queryList(Integer page, Integer size) {
        PageNationDTO pageNationDTO = new PageNationDTO();

        /*  将page和totalPage封装
            1. 根据totalCount计算出totalPage
            2. 对page进行容错处理
            3. 将page和totalPage封装进pageNationDTO中
         */
        int totalCount = questionMapper.count();
        int totalPage = (totalCount % size == 0) ? totalCount / size : totalCount / size + 1;
        if (page < 1)
            page = 1;
        if (page > totalPage)
            page = totalPage;
        pageNationDTO.setPageNation(totalPage, page);

        /*  将questionDTOS封装(他是一个混合集合，要进行组装)
            1. 根据偏移量offset和页大小size，查询出questionList
            2. 遍历questionList，根据creater属性，拿到user
            3. 将每一个question和user封装进questionDTO查询体中，最后放入questionDTOS集合
            4. 把集合封装进pageNationDTO中
         */
        int offset = size * (page - 1);
        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            // Spring提供的，快速封装查询体
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }

        pageNationDTO.setQuestionDTOS(questionDTOList);

        return pageNationDTO;
    }

    public PageNationDTO list(Integer userid, Integer page, Integer size) {
        PageNationDTO pageNationDTO = new PageNationDTO();

        int totalCount = questionMapper.countByUserId(userid);
        int totalPage = (totalCount % size == 0) ? totalCount / size : totalCount / size + 1;
        if (page < 1)
            page = 1;
        if (page > totalPage)
            page = totalPage;
        pageNationDTO.setPageNation(totalPage, page);

        int offset = size * (page - 1);
        List<Question> questionList = questionMapper.listById(userid, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            // Spring提供的，快速封装查询体
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }

        pageNationDTO.setQuestionDTOS(questionDTOList);
        return pageNationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.findById(id);
        User user = userMapper.findById(question.getCreater());

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
