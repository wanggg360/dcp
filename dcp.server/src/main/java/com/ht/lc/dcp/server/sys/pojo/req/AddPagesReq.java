package com.ht.lc.dcp.server.sys.pojo.req;

import com.ht.lc.dcp.server.sys.pojo.Page;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-12 19:52
 * @Version 1.0
 **/
public class AddPagesReq extends BaseReq {

    @NotEmpty List<Page> pages;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
