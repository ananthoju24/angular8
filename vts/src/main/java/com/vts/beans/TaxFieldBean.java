package com.vts.beans;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaxFieldBean implements Serializable {

	private static final long serialVersionUID = -8518010464342513321L;

	protected long houseTax;
	protected long libraryTax;
	protected long lightTax;
	protected long drainageTax;
	protected long waterTax;
	protected long kulaiNelaVariFee;
	protected long kulaiDeposit;
	protected long licenseFee;
	protected long houseConstructionFee;
	protected long dakhalaFee;
	protected long bandhelaDoddi;
	protected long buildingRents;
	protected String othersKey;
	protected long otherValue;
	
	//protected long cleaningTax;
	
}
