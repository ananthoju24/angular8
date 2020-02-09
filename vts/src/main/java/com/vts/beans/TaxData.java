package com.vts.beans;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TaxData {

	private int ep;
	private int gp;
	private int np;
	private int lp;
	private int pp;
	private String taxYear;
	private String paymentStatus;
	private int totalTaxAmount;

}
