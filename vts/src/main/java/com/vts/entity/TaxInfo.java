package com.vts.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.vts.util.TaxStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "T_TAX_DETAILS")
@Data
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class TaxInfo extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 104363229610299397L;

	@Column(name = "F_TAX_ID", nullable = false)
	@Id
	@SequenceGenerator(name = "taxId", initialValue = 100000)
	@GeneratedValue(generator = "taxId", strategy = GenerationType.SEQUENCE)
	private Long taxID;

	@ManyToOne
	@JoinColumn(name = "F_HOUSE_ID")
	private HouseInfo houseInfo;

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

	@Column(name = "F_TOTAL_TAX", nullable = false)
	protected long totalTax;

	@Column(name = "F_TAX_YEAR", nullable = false)
	protected String taxYear;

	@Column(name = "F_TAX_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	protected TaxStatus taxStatus;

}
