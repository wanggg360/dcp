package com.ht.lc.dcp.server.repository;

import com.ht.lc.dcp.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-08 08:37
 * @Version 1.0
 **/
public interface UserRepository extends JpaRepository<User, Integer> {

}
