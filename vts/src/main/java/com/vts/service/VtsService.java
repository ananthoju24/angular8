package com.vts.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.vts.beans.HouseOwnerBean;
import com.vts.beans.OwnerBean;
import com.vts.beans.PrintPageData;
import com.vts.beans.TaxDetails;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.TransactionBean;
import com.vts.beans.VtsTaxInfoBean;
import com.vts.entity.TaxInfo;
import com.vts.entity.TransactionInfo;
import com.vts.response.VTSRespone;

@Component
public interface VtsService {

	public boolean validateUser(String userName, String password);

	public TaxDetails fetchTaxInfo(String hno);
	
	public HouseOwnerBean getOwnerDetails(String houseNumber);

	public VTSRespone addOwner(OwnerBean owner);

	public HouseOwnerBean getOnwerDetails(String hno);

	public VTSRespone addTaxDetails(TaxInfoBean taxInfobean);

	public VtsTaxInfoBean getTaxDetails(String houseNumber);
	
	public TaxInfo getTaxDetails(String houseNumber, String taxYear);
	
	public List<TaxInfoBean> fetchTaxDetails(String houseNumber);
	
	public List<TaxInfoBean> fetchPrevTaxDetails(String houseNumber);
	
	public PrintPageData addTransaction(TaxInfoBean taxInfoBean);
	
	public List<TransactionBean> fetchTransaction(String houseNumber);
	
	public PrintPageData fetchTransactionById(String transactionID);
}
