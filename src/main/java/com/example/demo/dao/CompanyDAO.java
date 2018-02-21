package com.example.demo.dao;

import java.util.Collection;

import com.example.demo.entities.Company;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;

public interface CompanyDAO {

	/**
	 * Create company
	 * @param c
	 * @throws UserAlreadyExistsException
	 */
	void createCompany(Company c) throws UserAlreadyExistsException;

	/**
	 * Remove comapny
	 * @param c
	 * @throws UserNotFoundException
	 */
	void removeCompany(Company c) throws UserNotFoundException;

	/**
	 * Update company
	 * @param c
	 * @throws UserNotFoundException
	 * @throws IllegalUpdateException
	 */
	void updateCompany(Company c) throws UserNotFoundException, IllegalUpdateException;

	/**
	 * Get Company
	 * @param id
	 * @return
	 * @throws UserNotFoundException
	 */
	Company getCompany(long id) throws UserNotFoundException;

	/**
	 * Get all companies
	 * @return
	 * @throws UserNotFoundException
	 */
	Collection<Company> getAllCompanies() throws UserNotFoundException;
	
	/**
	 * Login as a company
	 * @param companyName
	 * @param password
	 * @return
	 * @throws UserNotFoundException
	 * @throws WrongPasswordException
	 */
	boolean login (String companyName, String password) 
			throws UserNotFoundException, WrongPasswordException;



}

