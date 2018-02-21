package com.example.demo.facades;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.*;
import com.example.demo.dbdao.CompanyDBDAO;
import com.example.demo.dbdao.CouponDBDAO;
import com.example.demo.dbdao.CustomerDBDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.CouponType;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponAlreadyPurchasedException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponOutOfStockException;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;


@Component
public class CustomerFacade implements CouponClientFacade {

	//Attributes
	@Autowired
	CustomerDBDAO customerDBDAO;

	@Autowired
	CouponDBDAO couponDBDAO;

	private Customer loginCustomer;

	//Getters and setters - for loginCustomer
	/**
	 * @return the loginCustomer
	 */
	public Customer getLoginCustomer() {
		return loginCustomer;
	}

	/**
	 * @param loginCustomer the loginCustomer to set
	 */
	public void setLoginCustomer(Customer loginCustomer) {
		this.loginCustomer = loginCustomer;
	}


	//Methods
	/*
	 * 
	 * Login as customer
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.facades.CouponClientFacade#login(java.lang.String, java.lang.String)
	 */
	@Override
	public CouponClientFacade login(String name, String password) 
			throws WrongPasswordException, UserNotFoundException {

		//If customer DBDAO returns true - set the customer and return this class
		if (customerDBDAO.login(name, password)) {

			//Set login customer here and in coupon DBDAO
			setLoginCustomer(customerDBDAO.getLoginCustomer());
			couponDBDAO.setLoginCustomer(this.loginCustomer);

			return this;
		}

		//Otherwise - return null
		else return null;
	}



	/**
	 * 
	 * Purchase coupon
	 * 
	 * @param c
	 */
	public void purchaseCoupon (Coupon c) {

		//Call coupon DBDAO  to purchase coupon
		try {
			couponDBDAO.purchaseCoupon(c);

			System.out.println("Coupon "+c.getTitle() +" was successfully purchased by customer " + loginCustomer.getCustomerName());

		} 
		catch (CouponNotFoundException e) {
			System.err.println( e.getMessage());
		} 
		catch (CouponAlreadyPurchasedException e) {
			System.err.println( e.getMessage());
		} 
		catch (CouponOutOfStockException e) {
			System.err.println( e.getMessage());
		} 
		catch (CouponExpiredException e) {
			System.err.println( e.getMessage());
		}
	}

/**
 * 
 * Get all purchased coupons
 * 
 * @return
 */
	public Collection<Coupon> getAllPurchasedCoupons() {
		//Call customer DBDAO to get all purchased coupons
		try {
			return customerDBDAO.getCoupons();
		} 
		catch (CouponNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
		}

	/**
	 * 
	 * Get all purchased coupons by type
	 *  
	 * @param type
	 * @return
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) {
		//Call coupon DBDAO to get all purchased coupons by type	
         try {
			return couponDBDAO.getAllPurchasedCouponsByType(type);
		} 
         catch (CouponNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
	}
		
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) {
		//Call coupon DBDAO to get all purchased coupons by price	
         try {
			return couponDBDAO.getAllPurchasedCouponsByPrice(price);
		} 
         catch (CouponNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
	}
	
	
}
