package com.vts.respository.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.entity.OwnerInfo;

public interface OwnerRepository extends JpaRepository<OwnerInfo, Long> {

}
