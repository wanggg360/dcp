package com.ht.lc.dcp.task.req;

import com.ht.lc.dcp.task.constant.BizConst;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-03-28 11:37
 * @Version 1.0
 **/


public class GatherNoticeDetailsReq extends BaseReq {

    @NotBlank
    @Size(min = 16, message = "size must larger than 16")
    private String taskId;

    @Pattern(regexp = BizConst.Common.PARAMS_DATE_FORMAT_PATTERN, message = "format yyyy-MM-dd")
    private String startDate = "1990-01-01";

    @Pattern(regexp = BizConst.Common.PARAMS_DATE_FORMAT_PATTERN, message = "format yyyy-MM-dd")
    private String endDate = "2200-12-31";

    private String branchId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
