package ru.kaleev.SpringCourse.entity;

import org.springframework.stereotype.Component;

@Component
public class Airline {
	private String codeAirline;
	private String nameAirline;
	private String address1;
	private String address2;
	private String telephone;
	private String identify;
	private int year;
	
	public String getCodeAirline() {
		return codeAirline;
	}
	public void setCodeAirline(String codeAirline) {
		this.codeAirline = codeAirline;
	}
	public String getNameAirline() {
		return nameAirline;
	}
	public void setNameAirline(String nameAirline) {
		this.nameAirline = nameAirline;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	
	
}
