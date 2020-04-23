package cn.edu.tit.forum.mapper;

import java.util.List;

public interface TagExtMapper {
    List<String> findCategory();

    List<String> findTags(String category);
}