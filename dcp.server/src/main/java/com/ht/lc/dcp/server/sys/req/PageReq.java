package com.ht.lc.dcp.server.sys.req;

import com.ht.lc.dcp.server.sys.entity.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-12 19:52
 * @Version 1.0
 **/
public class PageReq extends BaseReq {

    @Valid
    @NotEmpty
    List<Page> pages;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
