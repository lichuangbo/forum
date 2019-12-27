package cn.edu.tit.forum.cache;

import cn.edu.tit.forum.dto.HotTagDTO;
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
    private Map<String, Integer> tags = new HashMap<>();
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        // 构建小顶堆，求Top k
        PriorityQueue<HotTagDTO> minHeap = new PriorityQueue<>(Comparator.comparing(HotTagDTO::getWeight));
        tags.forEach(
                (name, weight) ->{
                    HotTagDTO hotTagDTO = new HotTagDTO();
                    hotTagDTO.setName(name);
                    hotTagDTO.setWeight(weight);
                    if (minHeap.size() < 5) {
                        minHeap.offer(hotTagDTO);
                    } else if (minHeap.peek().getWeight() < hotTagDTO.getWeight()){
                        minHeap.poll();
                        minHeap.offer(hotTagDTO);
                    }
                }
        );
        while (!minHeap.isEmpty() && hots.size() < 5) {
            HotTagDTO peek = minHeap.poll();
            hots.add(0, peek.getName());
        }
        System.out.println(hots);
    }
}
