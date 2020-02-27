package com.vts.beans;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrintPageData implements Serializable {
	
	private static final long serialVersionUID = -1053202476543413705L;
	private Long tranID;
	private HouseOwnerBean houseInfo;
	private String taxYear;
	private String data;
	private TransactionBean currentTax;
	private TransactionBean dueTax;

}
