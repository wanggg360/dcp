package com.ht.lc.dcp.server.sys.pojo.req;

import com.ht.lc.dcp.server.sys.pojo.Menu;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-12 19:52
 * @Version 1.0
 **/
public class AddMenusReq extends BaseReq {

    @NotEmpty List<Menu> menus;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
