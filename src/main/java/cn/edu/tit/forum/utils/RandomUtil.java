package cn.edu.tit.forum.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/5
 */
public class RandomUtil<T> {
    private List<T> ts;

    public RandomUtil(List<T> ts) {
        this.ts = ts;
    }

    public List<T> getRandomList(List<T> paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random random = new Random();
        List<Integer> ranList = new ArrayList<>();// 存储随机到的数字
        List<T> newList = new ArrayList<>();//
        int temp;
        for (int i = 0; i < count; i++) {
            temp = random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if (!ranList.contains(temp)) {
                ranList.add(temp);
                newList.add(paramList.get(temp));
            } else {
                i--;
            }
        }
        return newList;
    }
}
