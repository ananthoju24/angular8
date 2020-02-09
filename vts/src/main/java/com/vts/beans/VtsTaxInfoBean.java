package com.vts.beans;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VtsTaxInfoBean implements Serializable {

	private static final long serialVersionUID = 275694703030442877L;
	private String houseNumber;
	private HouseOwnerBean houseOwnerBean;
	private List<TaxInfoBean> taxBeanList;
	private TaxInfoBean currentTax;
	private TaxInfoBean dueTax;
	private String currentDate;
	private String taxYear;
}
