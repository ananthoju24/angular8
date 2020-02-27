package com.vts.respository.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.entity.TransactionInfo;

public interface TransactionRepository extends JpaRepository<TransactionInfo, Long>{

	
	@Query(value="Select tran.* From T_TRANSACTION_DETAILS tran, T_TAX_DETAILS tax where tax.F_HOUSE_ID= :houseNumber and tax.F_TAX_ID = tran.F_TAX_ID and tran.F_TAX_STATUS=:status", nativeQuery=true)
	public List<TransactionInfo> findBytaxID(String houseNumber, String status);
	
	
	public TransactionInfo findByTranID(long transactionID);
}
