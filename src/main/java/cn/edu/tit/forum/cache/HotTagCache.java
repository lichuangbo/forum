package cn.edu.tit.forum.cache;

import cn.edu.tit.forum.dto.HotTagDTO;
import cn.edu.tit.forum.dto.TagAttributes;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/24
 */
@Component
@Data
public class HotTagCache {
    private Map<String, TagAttributes> tags = new HashMap<>();
    private List<HotTagDTO> hotTagDTOS = new ArrayList<>();

    public void updateTags(Map<String, TagAttributes> tags) {
        // 构建小顶堆，求Top k
        PriorityQueue<HotTagDTO> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.getTagAttributes().getWeight()));
        tags.forEach(
                (name, tagAttributes) ->{
                    HotTagDTO hotTagDTO = new HotTagDTO();
                    hotTagDTO.setName(name);
                    hotTagDTO.setTagAttributes(tagAttributes);
                    if (minHeap.size() < 5) {
                        minHeap.offer(hotTagDTO);
                    } else if (minHeap.peek().getTagAttributes().getWeight() < hotTagDTO.getTagAttributes().getWeight()){
                        minHeap.poll();
                        minHeap.offer(hotTagDTO);
                    }
                }
        );
        while (!minHeap.isEmpty() && hotTagDTOS.size() < 5) {
            HotTagDTO peek = minHeap.poll();
            hotTagDTOS.add(0, peek);
        }
    }
}
