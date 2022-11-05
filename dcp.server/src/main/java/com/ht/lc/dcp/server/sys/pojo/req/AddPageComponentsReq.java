package com.ht.lc.dcp.server.sys.pojo.req;

import com.ht.lc.dcp.server.sys.pojo.PageComponent;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-09-22 19:21
 * @Version 1.0
 **/
public class AddPageComponentsReq extends BaseReq {

    @NotEmpty
    List<PageComponent> pageComponents;

    public List<PageComponent> getPageComponents() {
        return pageComponents;
    }

    public void setPageComponents(List<PageComponent> pageComponents) {
        this.pageComponents = pageComponents;
    }
}
