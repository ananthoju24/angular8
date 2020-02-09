package com.vts.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vts.beans.HouseOwnerBean;
import com.vts.beans.OwnerBean;
import com.vts.beans.TaxDetails;
import com.vts.beans.TaxInfoBean;
import com.vts.beans.VtsTaxInfoBean;
import com.vts.response.VTSRespone;

@Component
public interface VtsService {

	public boolean validateUser(String userName, String password);

	public TaxDetails fetchTaxInfo(String hno);
	
	public HouseOwnerBean getOwnerDetails(String houseNumber);

	public VTSRespone addOwner(OwnerBean owner);

	public HouseOwnerBean getOnwerDetails(String hno);

	public VtsTaxInfoBean addTaxDetails(TaxInfoBean taxInfobean);

	public VtsTaxInfoBean getTaxDetails(String houseNumber);
	
	public List<TaxInfoBean> fetchTaxDetails(String houseNumber);
}
