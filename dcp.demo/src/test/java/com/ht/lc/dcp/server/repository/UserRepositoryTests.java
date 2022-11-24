package com.ht.lc.dcp.server.repository;

import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.common.exception.ServiceException;
import com.ht.lc.dcp.demo.entity.User;
import com.ht.lc.dcp.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-11-23 16:52
 * @Version 1.0
 **/

@DataJpaTest @Import(SystemConfig.class) @AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED) public class UserRepositoryTests {

    @Autowired private UserRepository userRepository;

    @Test void addUser() {
        User user = new User();
        user.setUserId("018200");
        user.setUsername("wanggangww");
        user.setPassword("test");
        user.setCreateTime(LocalDateTime.now());
        user.setDepartmentCode("wwww");
        userRepository.save(user);
        User user1 = userRepository.findById(5).orElseThrow(() -> new ServiceException("9999", "can not find user"));
        assertThat(user1.getUsername()).isEqualTo(user.getUsername());
    }
}
