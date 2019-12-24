package com.demo.repository;

import com.demo.model.VmDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VmDetailRepository extends JpaRepository<VmDetail,Long> {

}
