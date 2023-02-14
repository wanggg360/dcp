package com.ht.lc.dcp.server.sys.pojo.req;

import com.ht.lc.dcp.common.constants.CommonConst;
import com.ht.lc.dcp.server.consts.BizConst;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-09-20 10:39
 * @Version 1.0
 **/
public class AddUserReq extends BaseReq {

    /**
     * 工号
     */
    @NotEmpty
    @Size(min = 5, max = 10, message = "size must larger than 5, smaller than 10")
    private String userId;

    /**
     * 全名
     */
    @NotEmpty
    private String fullName;

    /**
     * 密码
     */
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@!%*?&])[A-Za-z\\d$@!%*?&]{8,}", message = "password must contains letter, number, symbol ($@!%*?&) and length >= 8")
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 创建类型
     */
    @NotEmpty
    private String createType;

    /**
     * 部门编码
     */
    private List<String> departments;

    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 校验请求
     *
     * @return
     */
    public boolean isValidRequest() {
        boolean result = true;
        if (Objects.nonNull(getCreateType())) {
            result = Arrays.asList(BizConst.DictCollection.USER_CREATE_TYPE).contains(getCreateType());
        }
        if (StringUtils.hasText(getMobile())) {
            result = result && getMobile().matches(CommonConst.RegexRule.VALID_MOBILE);
        }
        if (StringUtils.hasText(getEmail())) {
            result = result && getEmail().matches(CommonConst.RegexRule.VALID_EMAIL);
        }
        return result;
    }
}
