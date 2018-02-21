package com.example.demo.entities;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name="COUPONS")
public class Coupon  implements Serializable {

	//Attributes (9)
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String title;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private int amount;

	@Column
	private CouponType type;
			
	@Column
	private String message;
	
	@Column
	private double price;

	@Column
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "company_id") //, nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Company company;
	
	
	
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH , CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "customer_coupon",
				joinColumns = @JoinColumn(name = "coupon_id"),
				inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Collection<Customer> customers;
	
	
	
	
	//Ctors

	public Coupon() {
		super();
	}


public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image, Collection<Customer> customers) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
		this.customers = customers;
	}



	//GS
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Date getStartDate() {
		return startDate;
	}


//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
	
	
	public void setStartDate(String stringDate){

	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        this.startDate  = dateFormat.parse(stringDate);
	    }

	    catch(ParseException e){
	    	System.err.println(stringDate +" is not a valid date.");
	    	//e.printStackTrace();

	    }
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(String stringDate){

	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        this.endDate  = dateFormat.parse(stringDate);
	    }

	    catch(ParseException e){
	    	System.err.println(stringDate +" is not a valid date.");
	    	//e.printStackTrace();

	    }
	}
	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public CouponType getType() {
		return type;
	}


	public void setType(CouponType type) {
		this.type = type;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

	
	
public Collection<Customer> getCustomers() {
		return customers;
	}


	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	//ToString
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	
	
	
	
	

}