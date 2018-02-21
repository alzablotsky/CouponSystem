package com.example.demo.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="COMPANIES")
public class Company implements Serializable {
	
//Attributes
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String companyName;
	

	@Column
	private String password;
	

	@Column
	private String email;

	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="company_id")
	private Collection<Coupon> coupons;
	
//Ctors
	
	public Company(String companyName, String password, String email, Collection<Coupon> coupons) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
		this.coupons= coupons;
		
	}

	public Company() {
		super();
	}
	
	

public Company(String companyName, String password, String email) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}

//G/S	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
		
//TS	
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}
	
	
	
}
