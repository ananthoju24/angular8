package com.vts.util;

public enum TaxStatus {

	SUCCESS("SUCCESS"), PENDING("PENDING"), PARTIAL("PARTIAL"), CANCEL("CANCEL"), FAILED("FAILED");

	private String status;

	TaxStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	
}
