package com.vts.beans;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = 8622967615991476287L;

	private String username;
	private String password;
}
