package com.vts.beans;

import java.io.Serializable;

import lombok.Data;

@Data
public class OwnerBean implements Serializable {

	private static final long serialVersionUID = 7745292733137984739L;
	private String ownerName;
	private String fhName;
	private String mobileNumber;
	private String houseNumber;

}
