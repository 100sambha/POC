package com.aithentic.model;

import java.util.ArrayList;
import java.util.List;


public class Product_User{
	List<String> BusinessPhones = new ArrayList<>();
	String DisplayName = null;
	String GivenName = null;
	String JobTitle = null;
	String Mail = null;
	String MobilePhone = null;
	String OfficeLocation = null;
	String PreferredLanguage = null;
	String Surname = null;
	String UserPrincipalName = null;
	String ID = null;

	public Product_User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product_User(List<String> businessPhones, String displayName, String givenName, String jobTitle, String mail,
			String mobilePhone, String officeLocation, String preferredLanguage, String surname,
			String userPrincipalName, String iD) {
		super();
		BusinessPhones = businessPhones;
		DisplayName = displayName;
		GivenName = givenName;
		JobTitle = jobTitle;
		Mail = mail;
		MobilePhone = mobilePhone;
		OfficeLocation = officeLocation;
		PreferredLanguage = preferredLanguage;
		Surname = surname;
		UserPrincipalName = userPrincipalName;
		ID = iD;
	}

	public List<String> getBusinessPhones() {
		return BusinessPhones;
	}

	public void setBusinessPhones(List<String> businessPhones) {
		BusinessPhones = businessPhones;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public String getGivenName() {
		return GivenName;
	}

	public void setGivenName(String givenName) {
		GivenName = givenName;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getMobilePhone() {
		return MobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		MobilePhone = mobilePhone;
	}

	public String getOfficeLocation() {
		return OfficeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		OfficeLocation = officeLocation;
	}

	public String getPreferredLanguage() {
		return PreferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		PreferredLanguage = preferredLanguage;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public String getUserPrincipalName() {
		return UserPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		UserPrincipalName = userPrincipalName;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return "Product_User [BusinessPhones=" + BusinessPhones + ", DisplayName=" + DisplayName + ", GivenName="
				+ GivenName + ", JobTitle=" + JobTitle + ", Mail=" + Mail + ", MobilePhone=" + MobilePhone
				+ ", OfficeLocation=" + OfficeLocation + ", PreferredLanguage=" + PreferredLanguage + ", Surname="
				+ Surname + ", UserPrincipalName=" + UserPrincipalName + ", ID=" + ID + "]";
	}
}