package com.vts.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxInfoBean implements Serializable {

	private static final long serialVersionUID = 570181245958103777L;
	private Long taxId;
	private String houseNumber;
	private long houseTax;
	private long libraryTax;
	private long lightTax;
	private long drainageTax;
	private long waterTax;
	private long kulaiNelaVariFee;
	private long kulaiDeposit;
	private long licenseFee;
	private long houseConstructionFee;
	private long dakhalaFee;
	private long bandhelaDoddi;
	private long buildingRents;
	private String othersKey;
	private long otherValue;
	private long totalTax;
	private String taxYear;
	private String taxStatus;
	private String paymentType;
	
	public long getTotalTax() {
		this.totalTax = this.houseTax + this.libraryTax + this.lightTax + this.drainageTax + this.waterTax
				+ this.kulaiNelaVariFee + this.kulaiDeposit + this.licenseFee + this.houseConstructionFee
				+ this.dakhalaFee + this.bandhelaDoddi + this.buildingRents + this.otherValue;
		return totalTax;
	}

	public String getTaxStatus() {
		if (null == this.taxStatus) {
			return "PENDING";
		}
		return this.taxStatus;
	}

}
