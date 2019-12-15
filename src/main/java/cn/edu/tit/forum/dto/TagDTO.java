package cn.edu.tit.forum.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
