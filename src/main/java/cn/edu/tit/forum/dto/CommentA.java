package cn.edu.tit.forum.dto;

import lombok.Data;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/25
 */
@Data
public class CommentA {
    private int count;
    private CommentD comments[];
}
