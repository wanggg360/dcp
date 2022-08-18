package com.ht.lc.dcp.server.sys.dao;

import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.server.sys.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
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

        List<User> aa = new ArrayList<>(4);
        User a = new User();
        a.setUserId("018222");
        a.setPassword("wannggggg");
        a.setFullName("wang gang");
        a.setDepartmentCode("zz000012");
        //userDao.insert(a);
        User b = new User();
        b.setUserId("018220");
        b.setPassword("123");
        b.setFullName("wang gang");
        b.setDepartmentCode("zz000012");
        User c = new User();
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
        User user = new User();
        user.setFullName("wang gang");
        List<User> users = userDao.findUser(user);
        System.out.println(users.toString());
    }
}
