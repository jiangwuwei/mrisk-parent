package com.zoom.risk.operating.roster.vo;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class Page {
    private int totalCount;
    private int totalPage;
    private int currentPage;
    private int pageSize = 10;

    public Page(){
    }

    public Page(int totalCount, int currentPage){
        this.totalCount = totalCount;
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return (totalCount-1)/pageSize + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
