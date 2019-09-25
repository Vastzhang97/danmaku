package com.niit.util;

import org.springframework.stereotype.Component;

@Component
public class PageEntity {
    // 1.每页显示数量(everyPageNum)
    private int everyPageNum;
    // 2.总记录数(totalCount)
    private int totalCount;
    // 3.总页数(totalPage)
    private int totalPage;
    // 4.当前页(currentPage)
    private int currentPage;
    // 5.起始点(beginPage)
    private int beginPage;
    // 6.是否有上一页(hasPrePage)
    private boolean hasPrePage;
    // 7.是否有下一页(hasNextPage)
    private boolean hasNextPage;

    /**
     * @param everyPageNum 每页显示数量(everyPageNum)
     * @param totalCount   总记录数(totalCount)
     * @param totalPage    总页数(totalPage)
     * @param currentPage  当前页(currentPage)
     * @param beginPage   起始点(beginPage)
     * @param hasPrePage   是否有上一页(hasPrePage)
     * @param hasNextPage  是否有下一页(hasNextPage)
     */
    public PageEntity(int everyPageNum, int totalCount, int totalPage, int currentPage,
                      int beginPage, boolean hasPrePage, boolean hasNextPage) {
        this.everyPageNum = everyPageNum;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.beginPage = beginPage;
        this.hasPrePage = hasPrePage;
        this.hasNextPage = hasNextPage;
    }

    //构造函数，默认
    public PageEntity() {
    }

    //构造方法，对所有属性进行设置


    public int getEveryPageNum() {
        return everyPageNum;
    }

    public void setEveryPageNum(int everyPageNum) {
        this.everyPageNum = everyPageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "everyPageNum=" + everyPageNum +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", beginPage=" + beginPage +
                ", hasPrePage=" + hasPrePage +
                ", hasNextPage=" + hasNextPage +
                '}';
    }
}
