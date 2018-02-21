package com.example.demo.dbdao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CouponDAO;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.CouponType;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponAlreadyExistsException;
import com.example.demo.exceptions.CouponAlreadyPurchasedException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponOutOfStockException;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.repo.CouponRepo;

@Component
public class CouponDBDAO implements CouponDAO {

	//Attributes
	@Autowired
	CouponRepo couponRepo;

	private Company loginCompany;

	private Customer loginCustomer;


	//Getters and setters for login company and login customer

	/**
	 * 
	 * @return the loginCompany
	 */
	public Company getLoginCompany() {
		return loginCompany;
	}

	/**
	 * 
	 * @param loginCompany the loginCompany to set
	 */
	public void setLoginCompany(Company loginCompany) {
		this.loginCompany = loginCompany;
	}


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
	 * Check if a coupon with a certain title exists
	 * 
	 * @param title
	 * @return
	 */
	public boolean couponTitleExists (String title) {
		List<Coupon> coup = couponRepo.findCouponByTitle(title);
		if (coup.isEmpty()) return false;
		else return true;
	}




	/**
	 * 
	 * Check if a coupon with a certain  id and company id exists
	 * 
	 * @param id
	 * @param companyId
	 * @return
	 */
	public boolean couponIdCompanyIdExists (long id) {
		List<Coupon> coup = couponRepo.findCouponByIdAndCompanyId(id, this.loginCompany.getId());
		if (coup.isEmpty()) return false;
		else return true;
	}


	/*
	 * 
	 * Create coupon
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#createCoupon(com.example.demo.entities.Coupon)
	 */
	@Override
	public void createCoupon(Coupon c) throws CouponAlreadyExistsException {
		//If a coupon with this ID already exists - throw exception
		if (couponRepo.exists(c.getId())) {
			//this.couponIdExists(c.getId())) {
			throw new CouponAlreadyExistsException ("Cannot create new coupon. Coupon id=" + c.getId() + " already exists.");
		}

		//If a coupon with this name already exists - throw exception
		if (this.couponTitleExists(c.getTitle())) {
			throw new CouponAlreadyExistsException ("Cannot create new coupon. Coupon title " + c.getTitle() + " already exists.");
		}
		//Otherwise - create coupon	
		else {
			couponRepo.save(c);
		}

	}
	/*
	 * 
	 * Remove coupon
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#removeCoupon(com.example.demo.entities.Coupon)
	 */
	@Override
	public void removeCoupon(Coupon c) throws CouponNotFoundException {

		//If a coupon with this ID of the company with this ID does not exist - throw exception
		if(!couponIdCompanyIdExists(c.getId())) {
			throw new CouponNotFoundException ("Cannot remove coupon. "
					+ "Coupon " + c + " of company " + this.loginCompany.getCompanyName()
					+ " does not exist.");
		}

		//Otherwise - remove coupon	
		else {
			couponRepo.removeCoupon(c.getId(), this.loginCompany.getId());
		}

	}

	/*
	 * 
	 * Update coupon
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#updateCoupon(com.example.demo.entities.Coupon)
	 */
	@Override
	public void updateCoupon(Coupon c) throws CouponNotFoundException, IllegalUpdateException {
		//Compare to the coupon in DB
		Coupon couponInDb= couponRepo.findByTitleAndCompanyId(c.getTitle(),this.loginCompany.getId());

		//If a coupon with this title of this company does not exist in DB - throw exception
		if(couponInDb==null) {
			throw new CouponNotFoundException ("Cannot update coupon. "
					+ "Coupon " + c + " of company " + this.loginCompany.getCompanyName()
					+ " does not exist.");
		}

		//If the coupon attributes other than END DATE and PRICE 
		//are changed - throw exception:

		//1.ID
		else if (couponInDb.getId()!=(c.getId())) {
			throw new IllegalUpdateException ("Cannot update coupon "+ c.getTitle() 
			+  ". Coupon id cannot be changed."); 
		}

		//2. Start date
		else if (!couponInDb.getStartDate().equals(c.getStartDate())) {
			throw new IllegalUpdateException ("Cannot update coupon "+ c.getTitle() 
			+  ". Start date cannot be changed."); 
		}

		//3. Amount
		else if (couponInDb.getAmount()!=(c.getAmount())) {
			throw new IllegalUpdateException ("Cannot update coupon "+ c.getTitle() 
			+  ". Amount cannot be changed."); 
		}

		//4. Type
		else if (!couponInDb.getType().equals(c.getType())) {
			throw new IllegalUpdateException ("Cannot update coupon "+ c.getTitle() 
			+  ". Coupon type cannot be changed."); 
		}

		//5. Message
		else if (!couponInDb.getMessage().equals(c.getMessage())) {
			throw new IllegalUpdateException ("Cannot update coupon "+ c.getTitle() 
			+  ". Message cannot be changed."); 
		}


		//6. Image
		else if (!couponInDb.getImage().equals(c.getImage())) {
			throw new IllegalUpdateException ("Cannot update coupon "+ c.getTitle() 
			+  ". Image cannot be changed."); 
		}


		//Otherwise - update coupon

		else {
			couponRepo.save(c);

		}

	}

	/*
	 *
	 * Get coupon by id and company id
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#getCoupon(long)
	 */
	@Override
	public Coupon getCoupon(long id) throws CouponNotFoundException {

		//If coupon with this ID of the company with this ID does not exist - throw exception
		if (!couponIdCompanyIdExists (id))  {
			throw new CouponNotFoundException ("Cannot display coupon details. "
					+ "Coupon id=" + id + " of company " + this.loginCompany.getCompanyName()
					+ " does not exist.");
		}
		//Otherwise - return coupon
		else {
			Coupon c = couponRepo.findByIdAndCompanyId(id, this.loginCompany.getId());
			return c;
		}

	}


	/*
	 * Get all company's coupons
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#getAllCoupons()
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponNotFoundException {
		Collection<Coupon> coupons =
				(Collection<Coupon>) couponRepo.findCouponByCompanyId(this.loginCompany.getId());

		//If no coupons exist - throw exception
		if (coupons.isEmpty())  {
			throw new CouponNotFoundException ("No coupons of company "
					+ this.loginCompany.getCompanyName() + " were found.");
		}

		//Otherwise - return all the coupons 
		else {
			return coupons;
		}
	}

	/*
	 * 
	 * Get company's coupons by type
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#getCouponByType(com.example.demo.entities.CouponType)
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) throws CouponNotFoundException {
		Collection<Coupon> coupons =
				(Collection<Coupon>) couponRepo.findCouponByTypeAndCompanyId(type, this.loginCompany.getId());	

		//If no coupons exist - throw exception
		if (coupons.isEmpty())  {
			throw new CouponNotFoundException ("No coupons of type "+ type + " of company "
					+ this.loginCompany.getCompanyName() + " were found.");
		}
		//Otherwise - return the coupons 
		else {
			return coupons;
		}
	}

	/*
	 * 
	 * Get company's  coupons under certain price
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#getCouponsUnderPrice(double)
	 */
	@Override
	public Collection<Coupon> getCouponsByPrice(double price) throws CouponNotFoundException {
		Collection<Coupon> coupons =
				(Collection<Coupon>) couponRepo.findByMaxPriceAndCompanyId(price, this.loginCompany.getId());
		//If no coupons exist - throw exception
		if (coupons.isEmpty())  {
			throw new CouponNotFoundException ("No coupons under price "+ price + " of company "
					+ this.loginCompany.getCompanyName() + " were found.");
		}
		//Otherwise - return the coupons 
		else {
			return coupons;
		}
	}


	/*
	 * 
	 * Get company's  coupons before certain date
	 * 
	 * (non-Javadoc)
	 * @see com.example.demo.dao.CouponDAO#getCouponsBeforeEndDate(java.util.Date)
	 */
	@Override
	public Collection<Coupon> getCouponsByEndDate(Date endDate) throws CouponNotFoundException {
		Collection<Coupon> coupons =
				(Collection<Coupon>) couponRepo.findByMaxEndDateAndCompanyId(endDate, this.loginCompany.getId());
		//If no coupons exist - throw exception
		if (coupons.isEmpty())  {
			throw new CouponNotFoundException ("No coupons with end date before "+ endDate + " of company "
					+ this.loginCompany.getCompanyName() + " were found.");
		}
		//Otherwise - return the coupons 
		else {
			return coupons;
		}
	}


	/**
	 * 
	 * Purchase coupon
	 * 
	 * @param c
	 * @throws CouponNotFoundException
	 * @throws CouponAlreadyPurchasedException
	 * @throws CouponOutOfStockException
	 * @throws CouponExpiredException
	 */
	public void purchaseCoupon (Coupon c) 
			throws CouponNotFoundException, CouponAlreadyPurchasedException, CouponOutOfStockException, CouponExpiredException {

		Collection <Customer> couponCustomers = c.getCustomers();	
		Date today= new Date();

		//If the coupon does not exist - throw exception
		if (couponRepo.findByTitle(c.getTitle())== null) {
			throw new CouponNotFoundException("Customer "+this.loginCustomer.getCustomerName()
			+" cannot purchase coupon. Coupon "+ c.getTitle()+ " does not exist.");	
		}

		//If the customer had already purchased this coupon - throw exception
		else if(couponRepo.findCustomerCoupon(this.loginCustomer.getId(), c.getId()) != null) {
			throw new CouponAlreadyPurchasedException("Customer "+this.loginCustomer.getCustomerName()
			+" cannot purchase coupon. Coupon "+ c.getTitle()+ " has already been purchased by this customer.");		
		}

		//If the coupon is out of stock - throw exception
		else if (c.getAmount()==0) {
			throw new CouponOutOfStockException("Customer "+this.loginCustomer.getCustomerName()
			+" cannot purchase coupon. Coupon "+ c.getTitle()+ " is out of stock.");	
		}	


		//If the coupon expired - throw exception
		else if (c.getEndDate().before(today)) {
			throw new CouponExpiredException("Customer "+this.loginCustomer.getCustomerName()
			+" cannot purchase coupon. Coupon "+ c.getTitle()+ " has expired.");		
		}

		//Otherwise - add the customer to coupon customers and update amount
		else {

			couponCustomers.add(this.loginCustomer);
			c.setAmount(c.getAmount()-1);
			couponRepo.save(c);
		}

	}	


	/**
	 * 
	 * Get purchased coupons by type
	 * 
	 * @param type
	 * @return
	 * @throws CouponNotFoundException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponNotFoundException {
		Collection<Coupon> coupons =
				(Collection<Coupon>) couponRepo.findCustomerCouponsByType(this.loginCustomer.getId(), type);	

		//If the customer does not have coupons of this type - throw exception
		if (coupons.isEmpty())  {
			throw new CouponNotFoundException ("Customer " + this.loginCustomer.getCustomerName() +
					" has not purchased coupons of type "+ type + ".");
		}
		//Otherwise - return the coupons 
		else {
			return coupons;
		}
	}


	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponNotFoundException {
		Collection<Coupon> coupons =
				(Collection<Coupon>) couponRepo.findCustomerCouponsByMaxPrice(this.loginCustomer.getId(), price);

		//If the customer does not have coupons under this price - throw exception
		if (coupons.isEmpty())  {
			throw new CouponNotFoundException ("Customer " + this.loginCustomer.getCustomerName() +
					" has not purchased coupons under price "+ price + ".");
		}

		//Otherwise - return the coupons 
		else {
			return coupons;
		}
	}




}
