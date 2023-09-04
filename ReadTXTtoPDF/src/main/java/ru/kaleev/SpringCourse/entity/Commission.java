package ru.kaleev.SpringCourse.entity;

import org.springframework.stereotype.Component;

@Component
public class Commission {
	private String codeAirline;
	private String year;
	private String ordinalNumber;
	private String identify;
	private String money;
	
	public String getCodeAirline() {
		return codeAirline;
	}
	public void setCodeAirline(String codeAirline) {
		this.codeAirline = codeAirline;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getOrdinalNumber() {
		return ordinalNumber;
	}
	public void setOrdinalNumber(String ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	
	
}
