package com.ht.lc.dcp.base;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-07-29 08:28
 * @Version 1.0
 **/

public class BaseRsp {

    private int resultCode;

    private String resultDesc;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
