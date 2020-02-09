package com.vts.response;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class VTSRespone implements Serializable {

	private static final long serialVersionUID = -1825351819586135027L;

	private int respCode;
	private String respDesc;
	private String authToken;
	private String userName;
}
