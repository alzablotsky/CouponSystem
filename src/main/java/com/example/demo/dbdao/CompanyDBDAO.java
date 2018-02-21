package com.example.demo.dbdao;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CompanyDAO;
import com.example.demo.entities.Company;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.repo.CompanyRepo;

@Component
public class CompanyDBDAO implements CompanyDAO{

	//Attributes
	@Autowired
	CompanyRepo companyRepo;

	private Company loginCompany;

//Getters and setters - for loginCompany 
	/**
	 * @return the loginCompany
	 */
	public Company getLoginCompany() {
		return loginCompany;
	}


	/**
	 * @param loginCompany the loginCompany to set
	 */
	public void setLoginCompany(Company loginCompany) {
		this.loginCompany = loginCompany;
	}

	//Methods

	/**
	 * Check if a company with a certain name exists
	 * 
	 * @param companyName
	 * @return
	 */
	public boolean companyNameExists (String companyName) {
		List<Company> comp = companyRepo.findCompanyByCompanyName(companyName);
		if (comp.isEmpty()) return false;
		else return true;
	}


	/*
	 * Create company
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CompanyDAO#createCompany(com.example.demo.entities.Company)
	 */

	@Override
	public void createCompany(Company c) throws UserAlreadyExistsException {
		//If a company with this ID already exists - throw exception
		if (companyRepo.exists(c.getId())) {
			throw new UserAlreadyExistsException ("Cannot create new company. Company id=" + c.getId() + " already exists.");
		}

		//If a company with this name already exists - throw exception
		if (this.companyNameExists(c.getCompanyName())) {
			throw new UserAlreadyExistsException ("Cannot create new company. Company name " + c.getCompanyName() + " already exists.");
		}
		// Otherwise -create company	
		else {
			companyRepo.save(c);
		}
	}


	/*
	 * Remove company
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CompanyDAO#removeCompany(com.example.demo.entities.Company)
	 */
	@Override
	public void removeCompany(Company c) throws UserNotFoundException {
		//If a company with this ID does not exist - throw exception
		if (!companyRepo.exists(c.getId())) {
			throw new UserNotFoundException ("Cannot remove company. Company "+ 
					c + " does not exist.");
		}

		//Otherwise - remove company	
		else {
			companyRepo.delete(c);
		}
	}


	/*
	 * Update company
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CompanyDAO#updateCompany(com.example.demo.entities.Company)
	 */
	@Override
	public void updateCompany(Company c) throws UserNotFoundException, IllegalUpdateException {
       //Compare to the company in DB
		Company companyInDb = companyRepo.findById(c.getId());

		//If a company with this ID does not exist in the DB - throw exception
		if (companyInDb == null)	{
			throw new UserNotFoundException ("Cannot update company. Company "+ 
					c + " does not exist.");
		}
		
		//If the company's name is changed - throw exception
		else if (!companyInDb.getCompanyName().equals(c.getCompanyName())) {
		
				throw new IllegalUpdateException ("Cannot update company "
					+  companyInDb.getCompanyName() 
					+  ". Company name cannot be changed."); 
		}

		//Otherwise - save the updated company
		else {
			companyRepo.save(c);
		}
		
	}
		

/*
 * Get company by id
 * 
 * (non-Javadoc)
 * @see com.example.demo.dao.CompanyDAO#getCompany(long)
 */
@Override
public Company getCompany(long id) throws UserNotFoundException {

	//If a company with this ID does not exist - throw exception
	if (!companyRepo.exists(id))  {
		throw new UserNotFoundException ("Cannot display company details. "
				+ "Company id=" + id + " does not exist.");
	}
	//Otherwise - return a company
	else {
		
		Company c = companyRepo.findOne(id);
		return c;
	}

}

/*
 * Get all companies
 * 
 * (non-Javadoc)
 * @see com.example.demo.dao.CompanyDAO#getAllCompanies()
 */
@Override
public Collection<Company> getAllCompanies() throws UserNotFoundException {

	Collection<Company> comp = (Collection<Company>) companyRepo.findAll();

	//If no companies exist - throw exception
	if (comp.isEmpty())  {
		throw new UserNotFoundException ("No companies were found.");
	}

	//Otherwise - return all the companies 
	else {
		return comp;
	}
}

/*
 * Login as a company
 * 
 * (non-Javadoc)
 * @see com.example.demo.dao.CompanyDAO#login(java.lang.String, java.lang.String)
 */
@Override
public boolean login (String companyName, String password) 
		throws UserNotFoundException, WrongPasswordException{

	//If a company with this name does not exist - throw exception
	if (companyRepo.findByName(companyName)== null) {	
	
	throw new UserNotFoundException ("Login failed. Company name " + companyName + " does not exist.");	
	}

	//If the password does not fit the name	- throw exception
	else if (companyRepo.findByNameAndPwd(companyName, password)== null) {
		
			throw new WrongPasswordException("Login failed. Wrong name + password: "
					+ companyName + ", " + password);
		}
		//Otherwise -  set the company and return true
		else  {
			this.loginCompany = companyRepo.findByNameAndPwd(companyName, password);
			return true;
		}
	}
}	







