package com.ht.lc.dcp.server.sys.pojo.req;

import com.ht.lc.dcp.server.sys.pojo.Role;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-09-22 19:24
 * @Version 1.0
 **/
public class AddRolesReq extends BaseReq {

    @NotEmpty List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
