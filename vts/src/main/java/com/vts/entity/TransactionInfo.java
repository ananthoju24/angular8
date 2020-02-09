package com.vts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "T_TRANSACTION_DETAILS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionInfo extends TaxBaseFields {

	private static final long serialVersionUID = 104363229610299397L;

	@Column(name = "F_TRAN_ID", nullable = false)
	@Id
	@SequenceGenerator(name = "taxId", initialValue = 100000)
	@GeneratedValue(generator = "taxId", strategy = GenerationType.SEQUENCE)
	private Long taxID;

	@ManyToOne
	@JoinColumn(name = "F_TAX_ID")
	private TaxInfo taxInfo;

	/*
	 * public TransactionInfo(long houseTax, long villageTax, long waterTax, long
	 * lightingTax, long cleaningTax, long totalTax) { this.houseTax = houseTax;
	 * this.villageTax = villageTax; this.waterTax = waterTax; this.lightingTax =
	 * lightingTax; this.cleaningTax = cleaningTax; this.totalTax = totalTax; }
	 */

}
