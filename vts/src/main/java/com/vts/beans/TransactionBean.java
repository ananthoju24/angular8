package com.vts.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper =  false)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBean implements Serializable {

	private static final long serialVersionUID = 74735217591463507L;

	private Long tranID;
	private String houseNum;
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
	private long totalTax;
	private String taxYear;
	private String taxStatus;
	
	private PrintPageData ppData;
	
	
	public long getTotalTax() {
		this.totalTax = this.houseTax + this.libraryTax + this.lightTax + this.drainageTax + this.waterTax
				+ this.kulaiNelaVariFee + this.kulaiDeposit + this.licenseFee + this.houseConstructionFee
				+ this.dakhalaFee + this.bandhelaDoddi + this.buildingRents + this.otherValue;
		return totalTax;
	}


	public TransactionBean(Long tranID, String houseNum, long houseTax, long libraryTax, long lightTax, long drainageTax, long waterTax, long kulaiNelaVariFee, long kulaiDeposit, long licenseFee, long houseConstructionFee, long dakhalaFee,
			long bandhelaDoddi, long buildingRents, String othersKey, long otherValue, long totalTax, String taxYear, String taxStatus) {
		super();
		this.tranID = tranID;
		this.houseNum = houseNum;
		this.houseTax = houseTax;
		this.libraryTax = libraryTax;
		this.lightTax = lightTax;
		this.drainageTax = drainageTax;
		this.waterTax = waterTax;
		this.kulaiNelaVariFee = kulaiNelaVariFee;
		this.kulaiDeposit = kulaiDeposit;
		this.licenseFee = licenseFee;
		this.houseConstructionFee = houseConstructionFee;
		this.dakhalaFee = dakhalaFee;
		this.bandhelaDoddi = bandhelaDoddi;
		this.buildingRents = buildingRents;
		this.othersKey = othersKey;
		this.otherValue = otherValue;
		this.totalTax = totalTax;
		this.taxYear = taxYear;
		this.taxStatus = taxStatus;
	}
}
