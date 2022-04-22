package com.ht.lc.dcp.task.entity;

/**
 * @program: dcp
 * @description: 公告分页信息
 * @author: wanggang
 * @create: 2022-03-20 20:08
 * @Version 1.0
 **/
public class NoticePageInfo {

    private int totalCnt;

    private int pageCnt;

    private int pageSize;

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getPageCnt() {
        return pageCnt;
    }

    public void setPageCnt(int pageCnt) {
        this.pageCnt = pageCnt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
