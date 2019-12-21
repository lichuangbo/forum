package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.PageNationDTO;
import cn.edu.tit.forum.dto.QuestionDTO;
import cn.edu.tit.forum.dto.QuestionQueryDTO;
import cn.edu.tit.forum.mapper.QuestionExtMapper;
import cn.edu.tit.forum.mapper.QuestionMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.Question;
import cn.edu.tit.forum.model.QuestionExample;
import cn.edu.tit.forum.model.User;
import exception.CustomizeErrorCode;
import exception.CustomizeException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    // index页 话题列表展示
    public PageNationDTO queryList(Integer page, Integer size, String search) {
        PageNationDTO<QuestionDTO> pageNationDTO = new PageNationDTO<>();

        /*  将page和totalPage封装
            1. 根据totalCount计算出totalPage
            2. 对page进行容错处理
            3. 将page和totalPage封装进pageNationDTO中
         */
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        int totalCount = questionExtMapper.countBySearch(questionQueryDTO);
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            // Spring提供的，快速封装查询体
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }

        pageNationDTO.setData(questionDTOList);
        return pageNationDTO;
    }

    // 我的问题页 问题列表展示
    public PageNationDTO list(Long userid, Integer page, Integer size) {
        PageNationDTO<QuestionDTO> pageNationDTO = new PageNationDTO<>();

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreaterEqualTo(userid);
        int totalCount = (int) questionMapper.countByExample(example);

        int totalPage = (totalCount % size == 0) ? totalCount / size : totalCount / size + 1;
        if (page < 1)
            page = 1;
        if (page > totalPage)
            page = totalPage;
        pageNationDTO.setPageNation(totalPage, page);

        int offset = size * (page - 1);
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria()
                .andCreaterEqualTo(userid);
        example1.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            // Spring提供的，快速封装查询体
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }

        pageNationDTO.setData(questionDTOList);
        return pageNationDTO;
    }

    // 根据问题ID查询 dto
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);

        // 处理 question/{id} id手动输入，但查找不到的异常
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        User user = userMapper.selectByPrimaryKey(question.getCreater());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    // 处理发布页 和 编辑页两种发布语境
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            // 第一次创建
            questionMapper.insert(question);
        } else {
            // 更新
            Question updateQuestion = new Question();

            updateQuestion.setGmtModified(question.getGmtCreate());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample updateQuestionExample = new QuestionExample();
            updateQuestionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, updateQuestionExample);

            // 处理当一个页面在修改，另起一个窗口将问题删除的情况 提交不成功
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    // 增加阅读数， 根据问题ID
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        // 从数据库中更新，而不是查出来在更新+1
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelative(QuestionDTO questionDTO) {
        if (StringUtils.isEmpty(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String regexTag = StringUtils.replace(questionDTO.getTag(), ",", "|");
        Question question = new Question();
        question.setTag(regexTag);
        question.setId(questionDTO.getId());
        List<Question> questions = questionExtMapper.selectRelative(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(
                q -> {
                    QuestionDTO qDTO = new QuestionDTO();
                    BeanUtils.copyProperties(q, qDTO);
                    return qDTO;
                }
        ).collect(Collectors.toList());
        return questionDTOS;
    }
}
