package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/24
 */
@Data
public class HotTagDTO {
    // 标签名
    private String name;
    // 标签权重
    private Integer weight;
}
