package cn.edu.tit.forum.cache;

import cn.edu.tit.forum.dto.TagDTO;
import cn.edu.tit.forum.mapper.TagExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
@Component
public class TagCache {

    @Autowired
    private TagExtMapper tagExtMapper;

    public List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        List<String> categorys = tagExtMapper.findCategory();
        for (String category : categorys) {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setCategoryName(category);
            List<String> tags = tagExtMapper.findTags(category);
            tagDTO.setTags(tags);
            tagDTOS.add(tagDTO);
        }
        return tagDTOS;
    }

    /**
     * 过滤出非法的字符串
     *
     * @param tags 标签字符串
     * @return
     */
    public String filterInvalid(String tags) {
        String[] split = tags.split(",");
        List<TagDTO> tagDTOS = get();

        // flatMap可以将两层的循环转化为一层
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = null;
        if (split.length != 0) {
            invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        }
        return invalid;
    }
}
