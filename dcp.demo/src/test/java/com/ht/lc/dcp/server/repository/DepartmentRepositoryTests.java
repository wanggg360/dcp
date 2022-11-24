package com.ht.lc.dcp.server.repository;

import com.ht.lc.dcp.common.config.SystemConfig;
import com.ht.lc.dcp.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-11-23 16:52
 * @Version 1.0
 **/

@DataJpaTest @Import(SystemConfig.class) @AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED) public class DepartmentRepositoryTests {

    @Autowired private DepartmentRepository departmentRepository;

    @Test void addDepartment() {

    }
}
