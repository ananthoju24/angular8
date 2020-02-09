package com.vts.respository.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.entity.HouseInfo;

public interface HouseRepository extends JpaRepository<HouseInfo, String> {

}
