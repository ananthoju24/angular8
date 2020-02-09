package com.vts.service.impl;

import java.io.Serializable;

import lombok.Data;

@Data
public class DBResponse implements Serializable {

	private static final long serialVersionUID = -1249903876577204797L;

	private int code;
	private String Response;
}
