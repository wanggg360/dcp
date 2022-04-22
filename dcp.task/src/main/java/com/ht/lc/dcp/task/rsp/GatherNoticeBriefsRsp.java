package com.ht.lc.dcp.task.rsp;

import com.ht.lc.dcp.common.base.BaseRsp;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-28 11:38
 * @Version 1.0
 **/
public class GatherNoticeBriefsRsp extends BaseRsp {

    private String taskId;

    private int successCnt;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getSuccessCnt() {
        return successCnt;
    }

    public void setSuccessCnt(int successCnt) {
        this.successCnt = successCnt;
    }
}
