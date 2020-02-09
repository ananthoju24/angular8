package com.vts.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "T_USER_DETAILS")
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7793976415640793209L;
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "F_ID", nullable = false)
	private Long id;
	
	@Id
	@Column(name = "F_USER_ID", nullable = false)
	private String userName;

	@Column(name = "F_PASSWORD", nullable = false)
	private String password;

	// private String roleName;
}
