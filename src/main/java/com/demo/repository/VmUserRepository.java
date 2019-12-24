package com.demo.repository;

import com.demo.model.VmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VmUserRepository extends JpaRepository<VmUser,Long> {
    VmUser findByUsername(String username);
}
