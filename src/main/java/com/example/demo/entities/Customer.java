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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name="CUSTOMERS")
public class Customer  implements Serializable {
	
//Attributes
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String customerName;
	
	@Column
	private String password;
		
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH , CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "customer_coupon",
				joinColumns = @JoinColumn(name = "customer_id"),
				inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private Collection<Coupon> coupons;


	//Ctors
	public Customer() {
		super();
	}



	public Customer(String customerName, String password, Collection<Coupon> coupons) {
		super();
		this.customerName = customerName;
		this.password = password;
		this.coupons = coupons;
	}
	


	public Customer(String customerName, String password) {
		super();
		this.customerName = customerName;
		this.password = password;
		
	}


//G/S
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
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
		return "Customer [id=" + id + ", customerName=" + customerName + ", password=" + password + ", coupons="
				+ coupons + "]";
	}
	
	
	
}
