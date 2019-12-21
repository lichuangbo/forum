package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/21
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
