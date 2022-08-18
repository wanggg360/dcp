package com.ht.lc.dcp.demo.repository;

import com.ht.lc.dcp.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-08 08:37
 * @Version 1.0
 **/
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
