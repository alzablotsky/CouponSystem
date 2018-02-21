package com.example.demo.repo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.*;


@Repository
public interface CouponRepo extends CrudRepository<Coupon, Long>{

	List<Coupon> findCouponById(long id);
	List<Coupon> findCouponByTitle(String title);
	List<Coupon> findCouponByIdAndTitle(long id, String title);
	List<Coupon> findCouponByType(CouponType type);
	List<Coupon> findCouponByCompanyId(long id);
	List<Coupon> findCouponByIdAndCompanyId(long id, long companyId);
	List<Coupon> findCouponByTypeAndCompanyId(CouponType type, long companyId);



	/**
	 * 
	 * Find coupon by id
	 * 
	 * @param id
	 * @return
	 */
	@Query("SELECT c FROM COUPONS c WHERE c.id = :id") 
	Coupon findById(@Param("id") long id);


	/**
	 * 
	 * Find coupon by title
	 * 
	 * @param title
	 * @return
	 */
	@Query("SELECT c FROM COUPONS c WHERE c.title = :title") 
	Coupon findByTitle(@Param("title") String title);


	/**
	 * 
	 * Find company's coupon - by coupon id and company id
	 * 
	 * @param id
	 * @param companyId
	 * @return
	 */
	@Query("SELECT c FROM COUPONS c WHERE c.id = :id AND  c.company.id = :companyId") 
	Coupon findByIdAndCompanyId(@Param("id") long id, @Param("companyId") long companyId);


	/**
	 * 
	 * Find company's coupon - by title and company id
	 * 
	 * @param title
	 * @param companyId
	 * @return
	 */
	@Query("SELECT c FROM COUPONS c WHERE c.title = :title AND  c.company.id = :companyId") 
	Coupon findByTitleAndCompanyId(@Param("title") String title, @Param("companyId") long companyId);

	/**
	 * 
	 * Delete company's coupon
	 * 
	 * @param id
	 * @param companyId
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM COUPONS c WHERE c.id = :id AND  c.company.id = :companyId")
	void removeCoupon(@Param("id") long id, @Param("companyId") long companyId);


	/**
	 * 
	 * Find company's coupons - by maximal price and company id
	 * 
	 * @param price
	 * @param id
	 * @return
	 */
	@Query("SELECT c FROM COUPONS c WHERE c.price <= :price AND  c.company.id = :companyId") 
	Collection<Coupon> findByMaxPriceAndCompanyId( @Param("price") double price,  @Param("companyId") long id);

	/**
	 * 
	 * Find company's coupons - by maximal dale and company id
	 * 
	 * @param endDate
	 * @param id
	 * @return
	 */
	@Query("SELECT c FROM COUPONS c WHERE c.endDate <= :endDate AND  c.company.id = :companyId") 
	Collection<Coupon> findByMaxEndDateAndCompanyId( @Param("endDate") Date endDate,  @Param("companyId") long id);
	
	
	
	/**
	 * Find customer's coupon
	 * @param customerId
	 * @param couponId
	 * @return
	 */
	@Query("SELECT coup FROM COUPONS coup WHERE coup.id = :couponId AND coup.id IN (SELECT coup.id FROM coup.customers c WHERE c.id = :customerId)") 
	Coupon findCustomerCoupon(@Param("customerId") long customerId, @Param("couponId") long couponId);
	

	
	/**
	 * 
	 * Find all customer's coupons
	 * 
	 * @param customerId
	 * @return
	 */
	@Query("SELECT coup FROM COUPONS coup WHERE coup.id IN (SELECT coup.id FROM coup.customers c WHERE c.id = :customerId)") 
	Collection <Coupon> findCustomerCoupons(@Param("customerId") long customerId);
	


/**
 * 
 * Find customer's coupons by type
 * 
 * @param customerId
 * @param type
 * @return
 */
	@Query("SELECT coup FROM COUPONS coup WHERE coup.id IN (SELECT coup.id FROM coup.customers c WHERE c.id = :customerId) AND coup.type = :type") 
	Collection <Coupon> findCustomerCouponsByType(@Param("customerId") long customerId, @Param("type") CouponType type);
	
/**
 * 
 * Find customer's coupons by maximal price
 * 	
 * @param customerId
 * @param price
 * @return
 */
	@Query("SELECT coup FROM COUPONS coup WHERE coup.id IN (SELECT coup.id FROM coup.customers c WHERE c.id = :customerId) AND coup.price <= :price") 
	Collection <Coupon> findCustomerCouponsByMaxPrice(@Param("customerId") long customerId, @Param("price") double price);
	
	
	
	
	/***
	 * Find Coupon in Customer_Coupon table
	 * @param couponId
	 * @return
	 */
	@Query("SELECT coup FROM COUPONS coup WHERE coup.id IN (SELECT coup.id FROM coup.customers c WHERE coup.id = :couponId)") 
	Coupon findCouponInCustomersTable(@Param("couponId") long couponId);


}