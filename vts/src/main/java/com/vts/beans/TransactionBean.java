package com.vts.beans;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper =  false)
public class TransactionBean extends TaxFieldBean implements Serializable {

	private static final long serialVersionUID = 74735217591463507L;

	private String houseNum;
}
