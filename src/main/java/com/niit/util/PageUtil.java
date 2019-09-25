package com.niit.util;

import org.springframework.stereotype.Component;

@Component
public class PageUtil {
    /**
     * @param everyPageNum 每页显示数量(everyPageNum)
     * @param totalCount   总记录数(totalCount)
     * @param currentPage  当前页(currentPage)
     * @return
     */
    public static PageEntity createPage(int everyPageNum, int totalCount, int currentPage) {
        everyPageNum = getEveryPageNum(everyPageNum);
        currentPage = getCurrentPage(currentPage);
        int totalPage = getTotalPage(everyPageNum, totalCount);
        int beginIndex = getBeginIndex(everyPageNum, currentPage);
        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        return new PageEntity(everyPageNum, totalCount, totalPage, currentPage,
                beginIndex, hasPrePage, hasNextPage);
    }

    public static PageEntity createPage(PageEntity page, int totalCount) {
        int everyPage = getEveryPageNum(page.getEveryPageNum());
        int currentPage = getCurrentPage(page.getCurrentPage());
        int totalPage = getTotalPage(everyPage, totalCount);
        int beginIndex = getBeginIndex(everyPage, currentPage);
        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        return new PageEntity(everyPage, totalCount, totalPage, currentPage,
                beginIndex, hasPrePage, hasNextPage);
    }

    //设置每页显示记录数
    public static int getEveryPageNum(int everyPage) {
        return everyPage == 0 ? 10 : everyPage;
    }

    //设置当前页
    public static int getCurrentPage(int currentPage) {
        return currentPage == 0 ? 1 : currentPage;
    }

    //设置总页数,需要总记录数，每页显示多少
    public static int getTotalPage(int everyPage, int totalCount) {
        int totalPage = 0;
        if (totalCount % everyPage == 0) {
            totalPage = totalCount / everyPage;
        } else {
            totalPage = totalCount / everyPage + 1;
        }
        return totalPage;
    }

    //设置起始点，需要每页显示多少，当前页
    public static int getBeginIndex(int everyPage, int currentPage) {
        return (currentPage - 1) * everyPage;
    }

    //设置是否有上一页，需要当前页
    public static boolean getHasPrePage(int currentPage) {
        return currentPage == 1 ? false : true;
    }

    //设置是否有下一个，需要总页数和当前页
    public static boolean getHasNextPage(int totalPage, int currentPage) {
        return currentPage == totalPage || totalPage == 0 ? false : true;
    }

}
