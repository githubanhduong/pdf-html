package ru.kaleev.SpringCourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "mydatabase")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "author")
	private String author;	

	@Column(name = "role")
	private String role;	

	@Column(name = "electricity_bill")
	private Float electricity_bill;	
	
	@Column(name = "subsistence_fee")
	private Float subsistence_fee;	
	
	@Column(name = "tuition")
	private Float tuition;	

	public User() {
		
	}

//	public User(int id, String username, String password, String author, String role) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.password = password;
//		this.author = author;
//		this.role = role;
//	}


	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Float getElectricity_bill() {
		return electricity_bill;
	}

	public void setElectricity_bill(Float electricity_bill) {
		this.electricity_bill = electricity_bill;
	}

	public Float getSubsisyence_fee() {
		return subsistence_fee;
	}

	public void setSubsisyence_fee(Float subsisyence_fee) {
		this.subsistence_fee = subsisyence_fee;
	}

	public Float getTuition() {
		return tuition;
	}

	public void setTuition(Float tuition) {
		this.tuition = tuition;
	}
	
}
