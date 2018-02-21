package com.example.demo.dao;

import java.util.Collection;
import java.util.Date;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.CouponType;
import com.example.demo.exceptions.CouponAlreadyExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.IllegalUpdateException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.exceptions.UserNotFoundException;


public interface CouponDAO {

	/**
	 * 
	 * Create coupon
	 *  
	 * @param c
	 * @throws CouponAlreadyExistsException
	 */
	void createCoupon(Coupon c) throws CouponAlreadyExistsException;

	/**
	 * 
	 * Remove coupon
	 * 
	 * @param c
	 * @throws CouponNotFoundException
	 */
	void removeCoupon(Coupon c) throws CouponNotFoundException; 


	/**
	 * 
	 * Update coupon
	 * 
	 * @param c
	 * @throws CouponNotFoundException
	 * @throws IllegalUpdateException
	 */
	void updateCoupon(Coupon c) throws  CouponNotFoundException, IllegalUpdateException;

	/**
	 * 
	 * Get coupon
	 * 
	 * @param id
	 * @return
	 * @throws CouponNotFoundException
	 */
	Coupon getCoupon(long id) throws CouponNotFoundException;

	/**
	 * 
	 * Get all coupons
	 * 
	 * @return
	 * @throws CouponNotFoundException
	 */
	Collection <Coupon> getAllCoupons() throws CouponNotFoundException;

	/**
	 * 
	 * Get coupon by type
	 * 
	 * @param type
	 * @return
	 * @throws CouponNotFoundException
	 */
	Collection <Coupon> getCouponsByType(CouponType type)throws CouponNotFoundException;

	/**
	 * 
	 * Get coupons under price
	 * 
	 * @param price
	 * @return
	 * @throws CouponNotFoundException
	 */
	Collection<Coupon> getCouponsByPrice(double price) throws CouponNotFoundException;

	/**
	 * 
	 * Get coupons before end date
	 * 
	 * @param price
	 * @return
	 * @throws CouponNotFoundException
	 */
	Collection<Coupon> getCouponsByEndDate(Date endDate) throws CouponNotFoundException;

}
