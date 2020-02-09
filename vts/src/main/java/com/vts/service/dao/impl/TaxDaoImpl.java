package com.vts.service.dao.impl;

import org.springframework.stereotype.Component;

import com.vts.beans.TaxDetails;
import com.vts.service.dao.TaxDao;

@Component
public class TaxDaoImpl implements TaxDao {

	@Override
	public TaxDetails geTaxDetails(String hno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addTaxData(TaxDetails taxDataList) {
		// TODO Auto-generated method stub
		return 0;
	}
	/*
	 * private static Map<String, TaxDetails> taxDataMap = new HashMap<>();
	 * 
	 * static { TaxData data = new TaxData(575, 46, 0, 58, 58, "2018", "Pending",
	 * 737); TaxData dueData = new TaxData(575, 46, 0, 58, 58, "2017", "Pending",
	 * 737); List<TaxData> taxList = new ArrayList<TaxData>(); taxList.add(data);
	 * taxList.add(dueData); TaxDetails taxDataList = new TaxDetails();
	 * taxDataList.setHno("1-24"); taxDataList.setOwnerName("Gaddam Bojanna");
	 * taxDataList.setTaxDataList(taxList); taxDataList.setCurrentTaxData(data);
	 * taxDataList.setDueTaxData(dueData); taxDataMap.put("1-24", taxDataList);
	 * SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YYYY"); Date date =
	 * new Date(); taxDataList.setDate(formatter.format(date).toString()); }
	 * 
	 * @Override public TaxDetails geTaxDetails(String hno) { TaxDetails dataList =
	 * null; if (null != hno && taxDataMap.containsKey(hno)) { dataList =
	 * taxDataMap.get(hno); } return dataList; }
	 * 
	 * @Override public int addTaxData(TaxDetails taxDetails) { int respCode = -1;
	 * if (null != taxDetails && taxDataMap.containsKey(taxDetails.getHno())) {
	 * TaxDetails datalist = taxDataMap.get(taxDetails.getHno());
	 * System.out.println("Adding Data :: " + datalist); List<TaxData> taxList =
	 * datalist.getTaxDataList(); taxList.add(taxDetails.getTaxDataList().get(0));
	 * System.out.println("taxList  " + taxList); respCode = 0; } else { respCode =
	 * -2; } return respCode; }
	 */
}
