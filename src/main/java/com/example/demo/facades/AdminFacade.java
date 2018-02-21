package com.example.demo.facades;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.*;
import com.example.demo.dbdao.CompanyDBDAO;
import com.example.demo.dbdao.CustomerDBDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;



@Component
public class AdminFacade implements CouponClientFacade {
	
	//Attributes

	@Autowired
	CompanyDBDAO companyDBDAO;
	
	@Autowired
	CustomerDBDAO customerDBDAO;

	//Methods

	/*
	 *
	 * Login as admin
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.facades.CouponClientFacade#login(java.lang.String, java.lang.String)
	 */
	@Override
	public CouponClientFacade login(String name, String password) throws WrongPasswordException {

		if (name.toLowerCase().equals("admin") && password.equals("1234")){
			return this;
		}
		else
		{
			throw new WrongPasswordException("Login failed. Wrong name + password: "
					+ name + ", " + password);
		}

	}

	/**
	 * 
	 * Create company
	 * 
	 * @param c
	 */
	public void createCompany(Company c) {

		// call company DBDAO to create company...
		try {
			companyDBDAO.createCompany(c);
			System.out.println("Company "+ c.getCompanyName() +" was successfully created.");

		} catch (UserAlreadyExistsException e) {
			System.err.println( e.getMessage());
		}

	}	

	 
	/**
	 * 
	 * Remove company
	 * 
	 * @param c
	 */
	public void removeCompany(Company c) {

		// call company DBDAO to remove company...
		try {
			companyDBDAO.removeCompany(c);
			System.out.println("Company "+ c.getCompanyName() +" was successfully removed.");

		} catch (UserNotFoundException e) {
			System.err.println( e.getMessage());
		}

	}			

		
	/**
	 * 
	 * Update company
	 * 
	 * @param c
	 */
	public void updateCompany(Company c) {

		// call company DBDAO to update company...
		try {
			companyDBDAO.updateCompany(c);
			System.out.println("Company "+ c.getCompanyName() +" was successfully updated.\n"+
			                   "Company details: "+ c);

		} catch (UserNotFoundException e) {
			System.err.println( e.getMessage());
		}
		catch (IllegalUpdateException e) {
			System.err.println( e.getMessage());
		}
		
	}


 
 /**
  * 
  *  Get company
  * 
  * @param id
  * @return
  */
	public Company getCompany(long id) {

		// call company DBDAO to get company
		try {
			return companyDBDAO.getCompany(id);

		} catch (UserNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * Get all companies
	 * 
	 * @return
	 */
	public Collection <Company> getAllCompanies() {
		// call company DBDAO to get all companies...
		try {
			return companyDBDAO.getAllCompanies();

		} catch (UserNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 
	 * Create customer
	 * 
	 * @param c
	 */
	public void createCustomer(Customer c){
			// call customer DBDAO to create customer
		try {
				customerDBDAO.createCustomer(c);
				System.out.println("Customer "+ c.getCustomerName() +" was successfully created.");

			} catch (UserAlreadyExistsException e) {
				System.err.println( e.getMessage());
			}

		}	
	
		/**
		 * 
		 *  Remove customer
		 * 
		 * @param c
		 */
		public void removeCustomer(Customer c){
			// call customer DBDAO to remove customer	
			try {
					customerDBDAO.removeCustomer(c);
					System.out.println("Customer "+ c.getCustomerName() +" was successfully removed.");

				} catch (UserNotFoundException e) {
					System.err.println( e.getMessage());
				}

			}
		
		/**
		 * 
		 * Update customer
		 * 
		 * @param c
		 */
		public void updateCustomer(Customer c){
			// call customer DBDAO to update customer
			try {
				customerDBDAO.updateCustomer(c);
				System.out.println("Customer "+ c.getCustomerName() +" was successfully updated.\n"
						+ "Customer details: "+ c);
			} 
			catch (UserNotFoundException e) {
				System.err.println( e.getMessage());
			}
			catch (IllegalUpdateException e) {
				System.err.println( e.getMessage());
			}
		}
			
			
		/**
		 * 
		 * Get customer
		 * 
		 * @param id
		 * @return
		 */
		public Customer getCustomer(long id) {

			// call customer DBDAO to get a customer...
			try {
				return customerDBDAO.getCustomer(id);

			} catch (UserNotFoundException e) {
				System.err.println( e.getMessage());
			}
			return null;
		}
		
		
		/**
		 * 
		 * Get all customers
		 * 
		 * @return
		 */
		public Collection <Customer> getAllCustomers() {

			// call customer DBDAO to get all customers...
			try {
				return customerDBDAO.getAllCustomers();

			} catch (UserNotFoundException e) {
				System.err.println( e.getMessage());
			}
			return null;
		}
		
		

}
