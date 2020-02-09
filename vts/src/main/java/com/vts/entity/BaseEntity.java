package com.vts.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -5885982008462523631L;

	@Column(name = "F_CREATE_DATE", nullable = false)
	@CreationTimestamp
	private Date createDate;

	@Column(name = "F_UPDATE_DATE", nullable = false)
	@UpdateTimestamp
	private Date updateDate;
}
