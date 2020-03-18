package cn.edu.tit.forum;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/2/14
 */
public class RandomNumTest {
    public static void main(String[] args) {
        int d = (int) Math.round(Math.random() * (999999 - 100000) + 100000);
        System.out.println(d);
    }
}
