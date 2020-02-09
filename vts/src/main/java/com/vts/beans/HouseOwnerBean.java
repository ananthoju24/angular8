/**
 * 
 */
package com.vts.beans;

import java.io.Serializable;

import lombok.Data;

/**
 * @author aananthoju
 *
 */
@Data
public class HouseOwnerBean implements Serializable {

	private static final long serialVersionUID = -4501764445186806055L;

	private String houseNumber;
	private String ownerName;
	private String fatherOrHusband;
	private String mobileNumber;

}
