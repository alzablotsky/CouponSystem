package com.example.demo.dao;

import java.util.Collection;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;

public interface CustomerDAO {
	
	/**
	 * 
	 * Create customer
	 * 
	 * @param c
	 * @throws UserAlreadyExistsException
	 */
    void createCustomer(Customer c) throws UserAlreadyExistsException;
    
	/**
	 * 
	 * Remove customer
	 * 
	 * @param c
	 * @throws UserNotFoundException
	 */
	void removeCustomer(Customer c) throws UserNotFoundException; 
	
	/**
	 * 
	 * Update customer
	 * 
	 * @param c
	 * @throws UserNotFoundException
	 * @throws IllegalUpdateException
	 */
	void updateCustomer(Customer c) throws UserNotFoundException, IllegalUpdateException;
	
	/**
	 * 
	 * Get customer
	 * 
	 * @param id
	 * @return
	 * @throws UserNotFoundException
	 */
	Customer getCustomer(long id) throws UserNotFoundException;
	
	/**
	 * 
	 * Get all customers
	 * 
	 * @return
	 * @throws UserNotFoundException
	 */
	Collection <Customer> getAllCustomers() throws UserNotFoundException;
	
	
	/**
	 * 
	 * Get all customer's coupons
	 * 
	 * @return
	 * @throws CouponNotFoundException
	 */
	Collection <Coupon> getCoupons() throws CouponNotFoundException;
	
	/**
	 * 
	 * Login as a customer
	 * 
	 * @param customerName
	 * @param password
	 * @return
	 * @throws UserNotFoundException
	 * @throws WrongPasswordException
	 */
	boolean login (String customerName, String password) 
			throws UserNotFoundException, WrongPasswordException;
	
	
	
	


}
