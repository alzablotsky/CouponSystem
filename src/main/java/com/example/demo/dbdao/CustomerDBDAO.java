package com.example.demo.dbdao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CustomerDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponAlreadyExistsException;
import com.example.demo.exceptions.CouponAlreadyPurchasedException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponOutOfStockException;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.repo.CustomerRepo;

@Component
public class CustomerDBDAO implements CustomerDAO {

	//Attributes
	@Autowired
	CustomerRepo customerRepo;

	private Customer loginCustomer;

	//Getters and setters - for login customer

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

	/**
	 * 
	 * Check if a customer with a certain name exists
	 * 
	 * @param customerName
	 * @return
	 */
	public boolean customerNameExists (String customerName) {
		List<Customer> cust = customerRepo.findCustomerByCustomerName(customerName);
		if (cust.isEmpty()) return false;
		else return true;
	}


	/*
	 * 
	 * Create customer
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#createCustomer(com.example.demo.entities.Customer)
	 */
	@Override
	public void createCustomer(Customer c) throws UserAlreadyExistsException {
		//If a customer with this ID already exists - throw exception
		if (customerRepo.exists(c.getId())) {
			throw new UserAlreadyExistsException ("Cannot create new customer. Customer id=" + c.getId() + " already exists.");
		}

		//If a customer with this name already exists - throw exception
		if (this.customerNameExists(c.getCustomerName())) {
			throw new UserAlreadyExistsException ("Cannot create new customer. Customer name " + c.getCustomerName() + " already exists.");
		}
		//Otherwise - create a customer	
		else {
			customerRepo.save(c);
		}
	}


	/*
	 * 
	 * Remove customer
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#removeCustomer(com.example.demo.entities.Customer)
	 */
	@Override
	public void removeCustomer(Customer c) throws UserNotFoundException {

		//If a customer with this ID does not exist - throw exception
		if  (!customerRepo.exists(c.getId())) {
			throw new UserNotFoundException ("Cannot remove customer. Customer " + c 
					+ " does not exist.");
		}
		//Otherwise - remove customer
		else {
			customerRepo.delete(c);
		}

	}

	/*
	 * 
	 * Update customer
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#updateCustomer(com.example.demo.entities.Customer)
	 */
	@Override
	public void updateCustomer(Customer c) throws UserNotFoundException, IllegalUpdateException {
		//Compare to the customer in DB
		Customer customerInDb = customerRepo.findById(c.getId());

		//If a customer with this ID does not exist in the DB - throw exception 
		if  (customerInDb==null) { 
			throw new UserNotFoundException ("Cannot update customer. Customer " + c 
					+ " does not exist.");
		}

		//If a customer name was changed - throw exception		
		else if (!customerInDb.getCustomerName().equals(c.getCustomerName())) {

			throw new IllegalUpdateException ("Cannot update customer "
					+ customerInDb.getCustomerName()
					+ ". Customer name cannot be changed."); 
		}

		//Otherwise - update customer

		else {
			customerRepo.save(c);
		}
	}




	/*
	 * 
	 * Get customer by id
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#getCustomer(long)
	 */
	@Override
	public Customer getCustomer(long id) throws UserNotFoundException {
		//If the customer with this ID does not exist - throw exception
		if  (!customerRepo.exists(id))  {
			throw new UserNotFoundException ("Cannot display customer details. "
					+ "Customer id=" + id + " does not exist.");
		}
		//Otherwise - return customer
		else {
			List<Customer> cust = customerRepo.findCustomerById(id);
			Customer c= cust.get(0);
			return c;
		}
	}

	/*
	 * 
	 * Get all customers
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#getAllCustomers()
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws UserNotFoundException {

		Collection<Customer> cust = (Collection<Customer>) customerRepo.findAll();

		//If no customers exist - throw exception
		if (cust.isEmpty())  {
			throw new UserNotFoundException ("No customers were found.");
		}
		//Otherwise - return all the customers 
		else {
			return cust;
		}
	}


	/*
	 * 
	 * Get all customer's coupons
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#getCoupons()
	 */
	@Override
	public Collection<Coupon> getCoupons() throws CouponNotFoundException {
		Collection<Coupon> coupons = this.loginCustomer.getCoupons();

		//If the customer has no coupons - throw exception
		if (coupons.isEmpty()) {
			throw new CouponNotFoundException ("Customer "+ this.loginCustomer.getCustomerName()
			+ " does not have any coupons.");
		}
		
		//Otherwise - return customer's coupons
		else {
			return coupons;
		}
	}


	/*
	 * 
	 * Login as customer
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CustomerDAO#login(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean login(String customerName, String password) 
			throws UserNotFoundException, WrongPasswordException {

		//If a customer with this name does not exist - throw exception
		if (customerRepo.findByCustomerName(customerName)==null) {

			throw new UserNotFoundException ("Login failed. Customer name " 
					+ customerName + " does not exist.");
		}

		//If the password does not fit the name	- throw exception
		else if (customerRepo.findByCustomerNameAndPassword(customerName, password)==null){	

			throw new WrongPasswordException("Login failed. Wrong name + password: "
					+ customerName + ", " + password);
		}

		//Otherwise -  set the login customer and return true
		else {
			setLoginCustomer(customerRepo.findByCustomerNameAndPassword(customerName, password));
			return true;
		}

	}



}
