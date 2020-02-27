package com.vts.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public class TaxBaseFields extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2704883892573314278L;

	@Column(name = "F_HOUSE_TAX", nullable = false)
	protected long houseTax;
	
	@Column(name = "F_LIBRARY_TAX", nullable = false)
	protected long libraryTax;
	
	@Column(name = "F_LIGHTHING_TAX", nullable = false)
	protected long lightTax;
	
	@Column(name = "F_DRAINAGE_TAX", nullable = false)
	protected long drainageTax;
	
	@Column(name = "F_WATER_TAX", nullable = false)
	protected long waterTax;
	
	@Column(name = "F_KULAI_NELA_VARI_FEE", nullable = false)
	protected long kulaiNelaVariFee;
	
	@Column(name = "F_KULAI_DEPOSIT", nullable = false)
	protected long kulaiDeposit;
	
	@Column(name = "F_LICENSE_TAX", nullable = false)
	protected long licenseFee;
	
	@Column(name = "F_HOUSE_CON_FEE", nullable = false)
	protected long houseConstructionFee;
	
	@Column(name = "F_DAKHALA_FEE", nullable = false)
	protected long dakhalaFee;
	
	@Column(name = "F_BANDHELA_DODDI", nullable = false)
	protected long bandhelaDoddi;
	
	@Column(name = "F_BUILDING_TAX", nullable = false)
	protected long buildingRents;
	
	@Column(name = "F_OTHER_KEY", nullable = false)
	protected String othersKey;
	
	@Column(name = "F_OTHER_VALUE", nullable = false)
	protected long otherValue;

	@Column(name = "F_CLEANING_TAX", nullable = false)
	protected long cleaningTax;
	
	/*
	 * @Column(name = "F_VILLAGE_TAX", nullable = false) protected long villageTax;
	 */

	@Column(name = "F_TOTAL_TAX", nullable = false)
	protected long totalTax;

	@Column(name = "F_TAX_YEAR", nullable = false)
	protected String taxYear;

	@Column(name = "F_TAX_STATUS", nullable = false)
	protected String taxStatus;

}
