package com.aithentic.model;

import java.util.Date;

public class CustomerInput
{
	private String customerName;
	private int licenseNo;
	private Date startDate; 
	private Date expiryDate; 
	private boolean Status;
	
	public CustomerInput() {
		super();
	}

	public CustomerInput(String customerName, int licenseNo, Date startDate, Date expiryDate, boolean status) {
		super();
		this.customerName = customerName;
		this.licenseNo = licenseNo;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		Status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(int licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}
}