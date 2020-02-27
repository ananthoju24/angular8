package com.vts.respository.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.entity.TaxInfo;

public interface TaxRepository extends JpaRepository<TaxInfo, Long> {

	@Query("From T_TAX_DETAILS  where F_HOUSE_ID = :houseNumber order by F_TAX_YEAR desc")
	public List<TaxInfo> findByhouseNumber(String houseNumber); // and F_TAX_STATUS not in ('PARTIAL')

	// T_TAX_DETAILS possible status PARTIAL,PENDING,SUCCESS
	@Query("From T_TAX_DETAILS  where F_HOUSE_ID = :houseNumber and F_TAX_STATUS != :status order by F_TAX_YEAR desc")
	public List<TaxInfo> findByhouseNumberandTaxStatusNot(String houseNumber, String status);

	@Query(value = "SELECT tax.F_TAX_ID,tax.F_HOUSE_TAX - tran.F_HOUSE_TAX ,tax.F_LIBRARY_TAX -tran.F_LIBRARY_TAX,\r\n"
			+ "			tax.F_LIGHTHING_TAX- tran.F_LIGHTHING_TAX, tax.F_DRAINAGE_TAX -tran.F_DRAINAGE_TAX, tax.F_WATER_TAX - tran.F_WATER_TAX,\r\n"
			+ "			tax.F_KULAI_NELA_VARI_FEE-tran.F_KULAI_NELA_VARI_FEE, tax.F_KULAI_DEPOSIT - tran.F_KULAI_DEPOSIT,\r\n"
			+ "			tax.F_LICENSE_TAX - tran.F_LICENSE_TAX, tax.F_HOUSE_CON_FEE - tran.F_HOUSE_CON_FEE, tax.F_DAKHALA_FEE - tran.F_DAKHALA_FEE,\r\n"
			+ "			tax.f_bandhela_doddi -tran.f_bandhela_doddi , tax.F_BUILDING_TAX - tran.F_BUILDING_TAX,\r\n" + "			tax.F_OTHER_VALUE-tran.F_OTHER_VALUE, tax.F_TOTAL_TAX-tran.F_TOTAL_TAX ,\r\n"
			+ "			tax.F_CREATE_DATE,tax.F_UPDATE_DATE FROM T_TAX_DETAILS tax, T_TRANSACTION_DETAILS tran where tax.F_HOUSE_ID= :houseNumber and tax.F_TAX_ID = tran.F_TAX_ID and tax.F_TAX_STATUS = :status order by tax.F_TAX_YEAR desc", nativeQuery = true)
	public List<TaxInfo> findByhouseNumberandTaxStatus(String houseNumber, String status);

	/*
	 * @Query("FROM T_TAX_DETAILS where F_HOUSE_ID= :houseNumber and F_TAX_STATUS = :taxStatus"
	 * ) public List<TaxInfo> findByhouseNumber(String houseNumber, String
	 * taxStatus);
	 */

	@Query("From T_TAX_DETAILS where F_HOUSE_ID = :houseNumber and F_TAX_YEAR = :taxYear")
	public TaxInfo findByhouseNumber(String houseNumber, String taxYear);

	public TaxInfo findBytaxID(Long taxID);

	@Query(value = "select * From T_TAX_DETAILS  where F_HOUSE_ID = :houseNumber order by F_TAX_YEAR desc limit 1", nativeQuery = true)
	public List<TaxInfo> findByhouseNumberLimit1(String houseNumber);

	
	
	
	
	/*
	 * @Query("update T_TAX_DETAILS set F_TAX_STATUS = :status where F_TAX_ID = :taxID"
	 * ) public void updateTaxByTaxID(Long taxID, String status);
	 * 
	 * @Query("select new com.vts.entity.TaxInfo(SUM(houseTax),SUM(villageTax),SUM(waterTax),SUM(lightingTax),SUM(cleaningTax),SUM(totalTax)) "
	 * +
	 * "From T_TAX_DETAILS  where F_HOUSE_ID = :houseNumber and F_TAX_STATUS <> 'SUCCESS' and F_TAX_YEAR <> YEAR(CURDATE()) group by F_HOUSE_ID"
	 * ) public TaxInfo groupByHouseNumber(String houseNumber);
	 */
}