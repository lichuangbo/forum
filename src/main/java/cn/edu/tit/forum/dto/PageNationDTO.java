package cn.edu.tit.forum.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页封装工具类
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/9
 */
@Data
public class PageNationDTO<T> {
    // 展示的数据集合
    private List<T> data;
    // 按钮是否显示
    private Boolean showPrePage;
    private Boolean showFirstPage;
    private Boolean showNextPage;
    private Boolean showEndPage;
    // 当前页
    private Integer page;
    // 页数数组
    private List<Integer> pages = new ArrayList<>();
    // 总页数
    private Integer totalPage;

    /**
     * 封装显示逻辑
     * @param totalPage 总页数
     * @param page  当前第几页
     */
    public void setPageNation(Integer totalPage, Integer page) {
        this.page = page;
        this.totalPage = totalPage;

        pages.add(page);
        // 左显示3页，右显示3页
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 四个按钮显示逻辑
        showPrePage = page != 1;
        showNextPage = page != totalPage;
        showFirstPage = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);
    }
}
