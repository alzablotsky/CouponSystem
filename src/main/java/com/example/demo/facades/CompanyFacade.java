package com.example.demo.facades;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dbdao.CompanyDBDAO;
import com.example.demo.dbdao.CouponDBDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.CouponType;
import com.example.demo.exceptions.CouponAlreadyExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;



@Component
public class CompanyFacade implements CouponClientFacade {
	
	//Attributes
	@Autowired
	CompanyDBDAO companyDBDAO;

	@Autowired
	CouponDBDAO couponDBDAO;

	private Company loginCompany;


	//Getter only (setter is a part of login). 	
	public Company getLoginCompany() {
		return loginCompany;
	}


	//Methods
	/*
	 * Login as company
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.facades.CouponClientFacade#login(java.lang.String, java.lang.String)
	 */
	@Override
	public CouponClientFacade login(String companyName, String password) 
			throws WrongPasswordException, UserNotFoundException {

		// check if company DBDAO can login...
		if(companyDBDAO.login(companyName, password)){

			//Set company in this class	
			this.loginCompany = companyDBDAO.getLoginCompany();
			
			//Set company in coupon DBDAO
			couponDBDAO.setLoginCompany(this.loginCompany);
	
			return this;
		}

		else return null;
	}


	/**
	 * Create coupon
	 * 
	 * @param c
	 */
	public void createCoupon(Coupon c)	{
		try {
			// call coupon DBDAO to create a coupon
			couponDBDAO.createCoupon(c);
			System.out.println("Coupon "+ c.getTitle() +
					" was successfully created by " + this.loginCompany.getCompanyName());

			//add the created coupon to company's coupons collection
			Collection<Coupon> coupons = this.loginCompany.getCoupons();
			coupons.add(c);
			this.loginCompany.setCoupons(coupons);

			//call company DBDAO to update the company
			companyDBDAO.updateCompany(this.loginCompany);

		} 
		catch (CouponAlreadyExistsException e) {
			System.err.println( e.getMessage());
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
 * Remove coupon
 * 
 * @param c
 */
public void removeCoupon(Coupon c) 	{
	// call coupon DBDAO to remove coupon
	try {
		couponDBDAO.removeCoupon(c);
		System.out.println("Coupon "+ c.getTitle() +" was successfully removed.");
		
	} 
	catch (CouponNotFoundException e) {
		System.err.println( e.getMessage());
	}
	
}

/**
 * 
 * Update coupon
 * 
 * @param c
 */
public void updateCoupon(Coupon c) 	{
	
	try {
		couponDBDAO.updateCoupon(c);
		System.out.println("Coupon "+ c.getTitle() +" was successfully updated.");
		
	} 
	catch (CouponNotFoundException e) {
		System.err.println( e.getMessage());
	} 
	catch (IllegalUpdateException e) {
		System.err.println( e.getMessage());
	}
	}	


/**
 * 
 * Get coupon
 * 
 * @param id
 * @return
 */
public Coupon getCoupon(long id) 	{
		// call coupon DBDAO to get coupon
		try {
			return couponDBDAO.getCoupon(id);
			
		} 
		catch (CouponNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
}

/**
* 
* Get all coupons
* 	
* @return
*/
public Collection <Coupon> getAllCoupons() {
// call coupon DBDAO to get all coupons
try {
	return  couponDBDAO.getAllCoupons();
} 
catch (CouponNotFoundException e) {
	System.err.println( e.getMessage());
}
return null;
}

/**
 * 
 * Get coupons by type
 * 
 * @param type
 * @return
 */
public Collection <Coupon> getCouponsByType(CouponType type) {
	// call coupon DBDAO to get coupons by type
	try {
		return  couponDBDAO.getCouponsByType(type);
	} 
	catch (CouponNotFoundException e) {
		System.err.println( e.getMessage());
	}
	return null;
}

/**
 * 
 * Get coupons by price
 * 
 * @param price
 * @return
 */
public Collection <Coupon> getCouponsByPrice(double price) {
	// call coupon DBDAO to get coupons under this price
		try {
			return  couponDBDAO.getCouponsByPrice(price);
		} 
		catch (CouponNotFoundException e) {
			System.err.println( e.getMessage());
		}
		return null;
}

/**
 * 
 * Get coupons by end date
 * 
 * @param endDate
 * @return
 */
public Collection <Coupon> getCouponsByEndDate(Date endDate) {
	// call coupon DBDAO to get coupons before this end date
			try {
				return  couponDBDAO.getCouponsByEndDate(endDate);
			} 
			catch (CouponNotFoundException e) {
				System.err.println( e.getMessage());
			}
			return null;
	}
}









