package cn.edu.tit.forum;

import cn.edu.tit.forum.utils.DateUtil;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/4/5
 */
public class DateTest {
    public static void main(String[] args) {
        System.out.print("time=" + 1043733852779L);
        System.out.print("format time=" + DateUtil.format(1043733852779L));
    }
}
