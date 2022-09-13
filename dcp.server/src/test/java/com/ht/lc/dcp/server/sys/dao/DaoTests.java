package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.server.sys.daobean.UserDaoBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-08-09 18:42
 * @Version 1.0
 **/
@Import(SystemConfig.class)
@SpringBootTest
public class DaoTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void addUser() {

//        User user = new User();
//        user.setUserId("018208");
//        user.setFullName("wanggang");
//        List<User> lists = userDao.findUser(user);
//        System.out.println("www");

        List<UserDaoBean> aa = new ArrayList<>(4);
        UserDaoBean a = new UserDaoBean();
        a.setUserId("018222");
        a.setPassword("wannggggg");
        a.setFullName("wang gang");
        a.setDepartmentCode("zz000012");
        //userDao.insert(a);
        UserDaoBean b = new UserDaoBean();
        b.setUserId("018220");
        b.setPassword("123");
        b.setFullName("wang gang");
        b.setDepartmentCode("zz000012");
        UserDaoBean c = new UserDaoBean();
        c.setUserId("018221");
        c.setPassword("wannggggg");
        c.setFullName("wang gang");
        c.setDepartmentCode("zz000012");

        aa.add(a);
        aa.add(b);
        aa.add(c);
        userDao.insertBatch(aa);
    }

    @Test
    public void queryUser() {
        userDao.findByUserId("018220");
        UserDaoBean userDaoBean = new UserDaoBean();
        userDaoBean.setFullName("wang gang");
        List<UserDaoBean> userDaoBeans = userDao.findUser(userDaoBean);
        System.out.println(userDaoBeans.toString());
    }
}
