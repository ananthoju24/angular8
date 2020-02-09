package com.vts.service.dao;

import org.springframework.stereotype.Service;

import com.vts.beans.TaxDetails;

@Service
public interface TaxDao {

	public TaxDetails geTaxDetails(String hno);

	public int addTaxData(TaxDetails taxDataList);
}
