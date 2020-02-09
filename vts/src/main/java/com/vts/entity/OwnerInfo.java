package com.vts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "T_OWNER_DETAILS")
public class OwnerInfo extends BaseEntity {

	private static final long serialVersionUID = -7529422181008477720L;

	@SequenceGenerator(name = "ownerId", initialValue = 1000)
	@Column(name = "F_OWNER_ID", nullable = false)
	@Id
	@GeneratedValue(generator = "ownerId", strategy = GenerationType.SEQUENCE)
	private Long ownerID;

	@Column(name = "F_OWNER_NAME", nullable = false, unique = true)
	private String ownerName;

	@Column(name = "F_FATHER_OR_HUSBAND_NAME", nullable = false, unique = true)
	private String FatherOrHusband;
	
	@Column(name = "F_MOBILE_NUMBER", nullable = false)
	private String mobileNumber;

}
