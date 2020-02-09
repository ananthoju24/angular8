package com.vts.respository.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.entity.TaxInfo;

public interface TaxRepository extends JpaRepository<TaxInfo, Long> {

	@Query("From T_TAX_DETAILS  where F_HOUSE_ID = :houseNumber order by F_TAX_YEAR desc")
	public List<TaxInfo> findByhouseNumber(String houseNumber);

	/*
	 * @Query("select new com.vts.entity.TaxInfo(SUM(houseTax),SUM(villageTax),SUM(waterTax),SUM(lightingTax),SUM(cleaningTax),SUM(totalTax)) "
	 * +
	 * "From T_TAX_DETAILS  where F_HOUSE_ID = :houseNumber and F_TAX_STATUS <> 'SUCCESS' and F_TAX_YEAR <> YEAR(CURDATE()) group by F_HOUSE_ID"
	 * ) public TaxInfo groupByHouseNumber(String houseNumber);
	 */
}