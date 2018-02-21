package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entry.ClientType;
import com.example.demo.dao.*;
import com.example.demo.dbdao.CompanyDBDAO;
import com.example.demo.entities.*;
import com.example.demo.entry.CouponSystem;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.demo.repo.CompanyRepo;
import com.example.demo.repo.CouponRepo;
import com.example.demo.repo.CustomerRepo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

	//Attributes	
	@Autowired
	CouponSystem couponsystem;

	@Autowired
	CouponRepo couponRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CustomerRepo customerRepo;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	//Tests

	@Test
	public void test_001_contextLoads() {
	}

	//1. Test Admin Facade methods
	// login as admin
	@Test
	public void test_002_adminLogin() throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);

		Assert.assertNotNull(adminFacade);
	}

	@Test
	public void test_003_adminLogin() throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade1 = (AdminFacade) couponsystem.login("admin1", "1234", ClientType.ADMIN);

		Assert.assertNull(adminFacade1);
	}

	//create company

	@Test
	public void test_004_adminCreateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company("TEVA", "123", "teva@gmail.com");
		adminFacade.createCompany(comp);

		Assert.assertNotNull(companyRepo.findByName("TEVA"));
	}

	@Test
	public void test_005_adminCreateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company("GOOGLE", "234", "google@gmail.com");
		adminFacade.createCompany(comp);

		Assert.assertNotNull(companyRepo.findByName("GOOGLE"));
	}

	@Test
	public void test_006_adminCreateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company("AMDOCS", "345", "amdocs@gmail.com");
		adminFacade.createCompany(comp);

		Assert.assertNotNull(companyRepo.findByName("AMDOCS"));
	}

	@Test
	public void test_007_adminCreateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company("TEVA", "234", "teva@gmail.com");
		adminFacade.createCompany(comp);

		//Cannot create two companies with the same name
		Assert.assertFalse(companyRepo.findCompanyByCompanyName("TEVA").size()>1);
	}


	@Test
	public void test_008_adminCreateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company("SONOL", "444", "sonol@gmail.com");
		comp.setId(1);
		adminFacade.createCompany(comp);

		//Cannot create a company with id used by another company
		Assert.assertNull(companyRepo.findByName("SONOL"));

	}

	@Test
	public void test_009_adminCreateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company("SONOL", "444", "sonol@gmail.com");
		adminFacade.createCompany(comp);

		Assert.assertNotNull(companyRepo.findByName("SONOL"));
		//Assert.assertFalse(companyRepo.findCompanyByCompanyName("SONOL").isEmpty());
	}


	//remove company

	@Test
	public void test_010_adminRemoveCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= companyRepo.findByName("GOOGLE");
		adminFacade.removeCompany(comp);

		Assert.assertNull(companyRepo.findByName("GOOGLE"));

	}

	@Test
	public void test_011_adminRemoveCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company();
		comp.setCompanyName("HASHMAL");

		//cannot remove company that does not exist
		long countBefore = companyRepo.count();

		adminFacade.removeCompany(comp);

		long countAfter = companyRepo.count();
		Assert.assertEquals(countBefore, countAfter);

	}


	//update company

	@Test
	public void test_012_adminUpdateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= companyRepo.findByName("AMDOCS"); 
		comp.setPassword("456");
		adminFacade.updateCompany(comp);

		String updated= companyRepo.findByName("AMDOCS").getPassword();
		Assert.assertEquals("456", updated);

	}	

	@Test
	public void test_013_adminUpdateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= companyRepo.findByName("AMDOCS"); 
		comp.setEmail("amdocs1@gmail.com");
		adminFacade.updateCompany(comp);

		String updated= companyRepo.findByName("AMDOCS").getEmail();
		Assert.assertEquals("amdocs1@gmail.com", updated);
	}	

	@Test
	public void test_014_adminUpdateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= companyRepo.findByName("AMDOCS"); 
		comp.setCompanyName("AMDOCS1");
		adminFacade.updateCompany(comp);

		//Cannot update company name
		Assert.assertNull(companyRepo.findByName("AMDOCS1"));
	}	

	@Test
	public void test_015_adminUpdateCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= new Company();
		//comp.setId(25);
		comp.setCompanyName("HASHMAL");
		comp.setPassword("123");
		adminFacade.updateCompany(comp);

		//Cannot update company that does not exist in DB
		Assert.assertNull(companyRepo.findByName("HASHMAL"));
	}



	//get company by id

	@Test
	public void test_016_adminGetCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= adminFacade.getCompany(1);
		System.out.println("Company id 1: "+ comp);

		Assert.assertNotNull(comp);
		Assert.assertEquals(1, comp.getId());
	}


	@Test
	public void test_017_adminGetCompany() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Company comp= adminFacade.getCompany(6);
		System.out.println("Company id 6: "+ comp);

		Assert.assertNull(comp);

	}



	//get all companies
	@Test
	public void test_018_adminGetAllCompanies() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Collection <Company> comps= adminFacade.getAllCompanies();
		System.out.println("Companies list: \n"+ comps);

		Assert.assertNotNull(comps);
		Assert.assertEquals(companyRepo.count(), comps.size());

	}




	//create customer

	@Test
	public void test_019_adminCreateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= new Customer("Avi", "111");
		adminFacade.createCustomer(cust);

		Assert.assertNotNull(customerRepo.findByCustomerName("Avi"));
	}

	@Test
	public void test_020_adminCreateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= new Customer("Benny", "222");
		adminFacade.createCustomer(cust);

		Assert.assertNotNull(customerRepo.findByCustomerName("Benny"));
	}

	@Test
	public void test_021_adminCreateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= new Customer("Gabi", "333");
		adminFacade.createCustomer(cust);

		Assert.assertNotNull(customerRepo.findByCustomerName("Gabi"));
	}

	@Test
	public void test_022_adminCreateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= new Customer("Gabi", "444");
		adminFacade.createCustomer(cust);

		//Cannot create two customers with the same name
		Assert.assertFalse(customerRepo.findCustomerByCustomerName("Gabi").size()>1);
	}

	@Test
	public void test_023_adminCreateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= new Customer("Dudi", "555");
		adminFacade.createCustomer(cust);

		Assert.assertNotNull(customerRepo.findByCustomerName("Dudi"));
	}


	//remove customer

	@Test
	public void test_024_adminRemoveCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= customerRepo.findByCustomerName("Benny"); 

		adminFacade.removeCustomer(cust);

		Assert.assertNull(customerRepo.findByCustomerName("Benny"));
	}			

	@Test
	public void test_025_adminRemoveCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust=  new Customer ();
		cust.setCustomerName("Harel");

		//cannot remove customer that does not exist
		long countBefore = customerRepo.count();
		adminFacade.removeCustomer(cust);

		long countAfter = customerRepo.count();
		Assert.assertEquals(countBefore, countAfter);
	}

	//update customer

	@Test
	public void test_026_adminUpdateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= customerRepo.findByCustomerName("Gabi");
		cust.setPassword("444");
		adminFacade.updateCustomer(cust);

		Assert.assertEquals("444", customerRepo.findByCustomerName("Gabi").getPassword());


	}	

	@Test
	public void test_027_adminUpdateCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= customerRepo.findByCustomerName("Gabi");
		cust.setCustomerName("Gabriel");
		adminFacade.updateCustomer(cust);

		Assert.assertNull(customerRepo.findByCustomerName("Gabriel"));
	}


	//get customer by id

	@Test
	public void test_028_adminGetCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= adminFacade.getCustomer(1);
		System.out.println("Customer id 1: "+ cust);

		Assert.assertNotNull(cust);
		Assert.assertEquals(1, cust.getId());
	}

	@Test
	public void test_029_adminGetCustomer() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Customer cust= adminFacade.getCustomer(6);
		System.out.println("Customer id 6: "+ cust);

		Assert.assertNull(cust);
	}

	//get all customers

	@Test
	public void test_030_adminGetAllCustomers() 
			throws WrongPasswordException, UserNotFoundException	{

		AdminFacade adminFacade = (AdminFacade) couponsystem.login("admin", "1234", ClientType.ADMIN);
		Collection <Customer> custs= adminFacade.getAllCustomers();
		System.out.println("Customers list: \n"+ custs);

		Assert.assertNotNull(custs);
		Assert.assertEquals(customerRepo.count(), custs.size());

	}


	//2. Test Company Facade methods

	// login as a company
	@Test
	public void test_031_companyLogin() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);

		Assert.assertNotNull(teva);
	}


	@Test
	public void test_032_companyLogin() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade teva1 = (CompanyFacade) couponsystem.login("TEVA1", "123", ClientType.COMPANY);
		Assert.assertNull(teva1);
	}

	@Test
	public void test_033_companyLogin() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade teva2 = (CompanyFacade) couponsystem.login("TEVA", "1234", ClientType.COMPANY);
		Assert.assertNull(teva2);
	}


	// create coupon

	@Test
	public void test_034_companyCreateCoupon() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);

		Coupon coupon = new Coupon();
		coupon.setTitle("Free camping");
		coupon.setMessage("You can camp for free...");
		coupon.setAmount(2);
		coupon.setType(CouponType.CAMPING);
		coupon.setPrice(200);
		coupon.setStartDate("2017-06-01");
		coupon.setEndDate("2018-06-01");
		coupon.setImage("My image");

		teva.createCoupon(coupon);

		Assert.assertNotNull(couponRepo.findByTitle("Free camping"));
	}

	@Test
	public void test_035_companyCreateCoupon() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);

		Coupon coupon = new Coupon();
		coupon.setTitle("Dinner for two");
		coupon.setMessage("You can have a dinner...");
		coupon.setAmount(20);
		coupon.setType(CouponType.RESTAURANTS);
		coupon.setPrice(100);
		coupon.setStartDate("aaa");
		coupon.setStartDate("2017-01-01");
		coupon.setEndDate("2018-02-01");
		coupon.setImage("My image");

		teva.createCoupon(coupon);

		Assert.assertNotNull(couponRepo.findByTitle("Dinner for two"));
	}


	@Test
	public void test_036_companyCreateCoupon() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);

		Coupon coupon = new Coupon();
		coupon.setTitle("Free camping");
		coupon.setMessage("You can camp for free...");
		coupon.setAmount(20);
		coupon.setType(CouponType.TRAVELLING);
		coupon.setPrice(200);
		coupon.setStartDate("2018-06-01");
		coupon.setEndDate("2019-06-01");
		coupon.setImage("My image");

		teva.createCoupon(coupon);

		//Cannot create 2 coupons with the same name
		Assert.assertFalse(couponRepo.findCouponByTitle("Free camping").size()>1);
	}

	@Test
	public void test_037_companyCreateCoupon() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);

		Coupon coupon = new Coupon();
		coupon.setTitle("Flight to Ibiza");
		coupon.setMessage("You can flight to Ibiza...");
		coupon.setAmount(30);
		coupon.setType(CouponType.TRAVELLING);
		coupon.setPrice(150);
		coupon.setStartDate("2017-10-01");
		coupon.setEndDate("2019-10-01");
		coupon.setImage("My image");

		amdocs.createCoupon(coupon);

		Assert.assertNotNull(couponRepo.findByTitle("Flight to Ibiza"));
	}



	@Test
	public void test_038_companyCreateCoupon() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);

		Coupon coupon = new Coupon();
		coupon.setTitle("Gym membership");
		coupon.setMessage("You can go to a gym...");
		coupon.setAmount(30);
		coupon.setType(CouponType.SPORTS);
		coupon.setPrice(150);
		coupon.setStartDate("2017-05-01");
		coupon.setEndDate("2018-05-01");
		coupon.setImage("My image");

		amdocs.createCoupon(coupon);

		Assert.assertNotNull(couponRepo.findByTitle("Gym membership"));
	}




	@Test
	public void test_039_companyCreateCoupon() throws WrongPasswordException, UserNotFoundException	{

		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);

		Coupon coupon = new Coupon();
		coupon.setTitle("Shopping in the mall");
		coupon.setMessage("You can go shopping...");
		coupon.setAmount(30);
		coupon.setType(CouponType.FOOD);
		coupon.setPrice(500);
		coupon.setStartDate("2017-09-01");
		coupon.setEndDate("2018-09-01");
		coupon.setImage("My image");

		amdocs.createCoupon(coupon);

		Assert.assertNotNull(couponRepo.findByTitle("Shopping in the mall"));

	}

	//Remove coupon
	@Test
	public void test_040_companyRemoveCoupon() {
		Coupon coupon = couponRepo.findByTitle("Gym membership");
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.removeCoupon(coupon);

		Assert.assertNull(couponRepo.findByTitle("Gym membership"));

	}

	@Test
	public void test_041_companyRemoveCoupon() {
		Coupon coupon = couponRepo.findByTitle("Free camping");
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.removeCoupon(coupon);

		//Cannot remove coupon of another company
		Assert.assertNotNull(couponRepo.findByTitle("Free camping"));

	}

	//Update coupon

	//can update price and end date only


	@Test
	public void test_042_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setPrice(550);
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//can update price
		double updatePrice = couponRepo.findByTitle("Shopping in the mall").getPrice();
		Assert.assertEquals(coupon.getPrice(), updatePrice, 0.01);
	}

	@Test
	public void test_043_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setEndDate("2018-10-01");
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//can update end date
		Date updateEndDate = couponRepo.findByTitle("Shopping in the mall").getEndDate();
		Assert.assertEquals(coupon.getEndDate(), updateEndDate);
	}



	@Test
	public void test_044_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setId(10);
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update id
		long updateId = couponRepo.findByTitle("Shopping in the mall").getId();
		Assert.assertNotSame(coupon.getId(), updateId);
	}

	@Test
	public void test_045_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setStartDate("2019-01-01");
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update Start Date
		Date updateStartDate = couponRepo.findByTitle("Shopping in the mall").getStartDate();
		Assert.assertNotEquals(coupon.getStartDate(), updateStartDate);
	}

	@Test
	public void test_046_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setAmount(50);
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update amount
		int updateAmount = couponRepo.findByTitle("Shopping in the mall").getAmount();
		Assert.assertNotSame(coupon.getAmount(), updateAmount);
	}



	@Test
	public void test_047_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setType(CouponType.ELECTRICITY);
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update type
		CouponType updateType = couponRepo.findByTitle("Shopping in the mall").getType();
		Assert.assertNotEquals(coupon.getType(), updateType);
	}



	@Test
	public void test_048_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setMessage("Hello");
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update message
		String updateMessage = couponRepo.findByTitle("Shopping in the mall").getMessage();
		Assert.assertNotEquals(coupon.getMessage(), updateMessage);
	}

	@Test
	public void test_049_companyUpdateCoupon() {
		Coupon coupon = couponRepo.findByTitle("Shopping in the mall");
		coupon.setImage("Other image");
		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update image
		String updateImage = couponRepo.findByTitle("Shopping in the mall").getImage();
		Assert.assertNotEquals(coupon.getImage(), updateImage);
	}


	@Test
	public void test_050_companyUpdateCoupon() {
		Coupon coupon = new Coupon();
		coupon.setPrice(100);

		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		amdocs.updateCoupon(coupon);

		//Cannot update coupon that does not exist
		Assert.assertNull(couponRepo.findById(coupon.getId()));
	}

	//Get coupon

	@Test
	public void test_051_companyGetCoupon() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Coupon coupon = teva.getCoupon(1);
		System.out.println(coupon);

		Assert.assertNotNull(coupon);
		Assert.assertEquals(1, coupon.getId());
	}

	@Test
	public void test_052_companyGetCoupon() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Coupon coupon = teva.getCoupon(3);
		System.out.println(coupon);

		//Cannot get coupon that does not exist in this company		
		//Assert.assertNull(couponRepo.findByIdAndCompanyId(3, teva.getLoginCompany().getId()));

		Assert.assertNull(coupon);
	}

	//Get all coupons

	@Test
	public void test_053_companyGetAllCoupons() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Collection <Coupon> coupons = teva.getAllCoupons();
		System.out.println(coupons);

		Assert.assertNotNull(coupons);

		int sizeInDb= couponRepo.findCouponByCompanyId(teva.getLoginCompany().getId()).size();

		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_054_companyGetAllCoupons() {

		CompanyFacade amdocs = (CompanyFacade) couponsystem.login("AMDOCS", "456", ClientType.COMPANY);
		Collection <Coupon> coupons = amdocs.getAllCoupons();
		System.out.println(coupons);

		Assert.assertNotNull(coupons);

		int sizeInDb= couponRepo.findCouponByCompanyId(amdocs.getLoginCompany().getId()).size();

		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_055_companyGetAllCoupons() {

		CompanyFacade sonol = (CompanyFacade) couponsystem.login("SONOL", "444", ClientType.COMPANY);
		Collection <Coupon> coupons = sonol.getAllCoupons();

		//cannot get coupons if the company does not have any coupons
		Assert.assertNull(coupons);
	}

	//Get coupons by type
	@Test
	public void test_056_companyGetCouponsByType() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Collection <Coupon> coupons = teva.getCouponsByType(CouponType.CAMPING);
		System.out.println(coupons);

		Assert.assertNotNull(coupons);

		int sizeInDb= couponRepo.findCouponByTypeAndCompanyId(CouponType.CAMPING, teva.getLoginCompany().getId()).size();

		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_057_companyGetCouponsByType() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Collection <Coupon> coupons = teva.getCouponsByType(CouponType.FOOD);
		System.out.println(coupons);

		//cannot get coupons if the company does not have any coupons of this type
		Assert.assertNull(coupons);

	}

	//Get coupons by price

	@Test
	public void test_058_companyGetCouponsByPrice() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Collection <Coupon> coupons = teva.getCouponsByPrice(200);
		System.out.println(coupons);

		Assert.assertNotNull(coupons);

		int sizeInDb= couponRepo.findByMaxPriceAndCompanyId(200, teva.getLoginCompany().getId()).size();

		Assert.assertEquals(sizeInDb, coupons.size());

	}


	@Test
	public void test_059_companyGetCouponsByPrice() {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Collection <Coupon> coupons = teva.getCouponsByPrice(50);
		System.out.println(coupons);

		//cannot get coupons if the company does not have any coupons under this price
		Assert.assertNull(coupons);
	}

	//Get coupons by end date

	@Test
	public void test_060_companyGetCouponsByEndDate() throws ParseException {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Date endDate=this.dateFormat.parse("2018-07-01");
		Collection <Coupon> coupons = 
				teva.getCouponsByEndDate(endDate);
		System.out.println(coupons);

		Assert.assertNotNull(coupons);

		int sizeInDb= couponRepo.findByMaxEndDateAndCompanyId(endDate,
				teva.getLoginCompany().getId()).size();

		Assert.assertEquals(sizeInDb, coupons.size());

	}


	@Test
	public void test_061_companyGetCouponsByEndDate() throws ParseException {

		CompanyFacade teva = (CompanyFacade) couponsystem.login("TEVA", "123", ClientType.COMPANY);
		Date endDate=this.dateFormat.parse("2018-01-01");
		Collection <Coupon> coupons = 
				teva.getCouponsByEndDate(endDate);
		System.out.println(coupons);

		//cannot get coupons if the company does not have any coupons ending before this date
		Assert.assertNull(coupons);
	}


	//3. Test Customer Facade methods

	// login as a customer
	@Test
	public void test_062_customerLogin() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);

		Assert.assertNotNull(avi);
	}


	@Test
	public void test_063_customerLogin() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "123", ClientType.CUSTOMER);

		Assert.assertNull(avi);
	}



	@Test
	public void test_064_customerLogin() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade harel = (CustomerFacade) couponsystem.login("Harel", "123", ClientType.CUSTOMER);

		Assert.assertNull(harel);
	}


	//Purchase coupon

	@Test
	public void test_065_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Shopping in the mall");

		int amountBefore= couponRepo.findByTitle("Shopping in the mall").getAmount();
		avi.purchaseCoupon(c);

		int amountAfter= couponRepo.findByTitle("Shopping in the mall").getAmount();

		Assert.assertNotNull(couponRepo.findCustomerCoupon(avi.getLoginCustomer().getId(), c.getId()));
		Assert.assertEquals(amountBefore -1, amountAfter);

	}

	@Test
	public void test_066_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Free camping");

		int amountBefore= couponRepo.findByTitle("Free camping").getAmount();
		avi.purchaseCoupon(c);

		int amountAfter= couponRepo.findByTitle("Free camping").getAmount();

		Assert.assertNotNull(couponRepo.findCustomerCoupon(avi.getLoginCustomer().getId(), c.getId()));
		Assert.assertEquals(amountBefore -1, amountAfter);

	}

	@Test
	public void test_067_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Coupon c = new Coupon();
		c.setTitle("Empty coupon");

		avi.purchaseCoupon(c);

		//Cannot purchase coupon that does not exist
		Assert.assertNull(couponRepo.findCustomerCoupon(avi.getLoginCustomer().getId(), c.getId()));

	}	

	@Test
	public void test_068_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Shopping in the mall");

		int sizeBefore = couponRepo.findCustomerCoupons(avi.getLoginCustomer().getId()).size();

		//Cannot purchase coupon that was already purchased by this customer
		avi.purchaseCoupon(c);

		int sizeAfter = couponRepo.findCustomerCoupons(avi.getLoginCustomer().getId()).size();

		Assert.assertEquals(sizeBefore, sizeAfter);

	}


	@Test
	public void test_069_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Free camping");

		int amountBefore= couponRepo.findByTitle("Free camping").getAmount();

		gabi.purchaseCoupon(c);

		int amountAfter= couponRepo.findByTitle("Free camping").getAmount();

		Assert.assertNotNull(couponRepo.findCustomerCoupon(gabi.getLoginCustomer().getId(), c.getId()));
		Assert.assertEquals(amountBefore -1, amountAfter);

	}

	@Test
	public void test_070_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Flight to Ibiza");

		int amountBefore= couponRepo.findByTitle("Flight to Ibiza").getAmount();

		gabi.purchaseCoupon(c);

		int amountAfter= couponRepo.findByTitle("Flight to Ibiza").getAmount();

		Assert.assertNotNull(couponRepo.findCustomerCoupon(gabi.getLoginCustomer().getId(), c.getId()));
		Assert.assertEquals(amountBefore -1, amountAfter);

	}

	@Test
	public void test_071_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade dudi = (CustomerFacade) couponsystem.login("Dudi", "555", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Free camping");

		int amountBefore= couponRepo.findByTitle("Free camping").getAmount();

		dudi.purchaseCoupon(c);

		int amountAfter= couponRepo.findByTitle("Free camping").getAmount();

		//Cannot purchase coupon that is out of stock
		Assert.assertNull(couponRepo.findCustomerCoupon(dudi.getLoginCustomer().getId(), c.getId()));
		Assert.assertEquals(amountBefore, amountAfter);

	}


	@Test
	public void test_072_customerPurchaseCoupon() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade dudi = (CustomerFacade) couponsystem.login("Dudi", "555", ClientType.CUSTOMER);
		Coupon c = couponRepo.findByTitle("Dinner for two");

		int amountBefore= couponRepo.findByTitle("Dinner for two").getAmount();

		dudi.purchaseCoupon(c);

		int amountAfter= couponRepo.findByTitle("Dinner for two").getAmount();


		//Cannot purchase coupon that has expired
		Assert.assertNull(couponRepo.findCustomerCoupon(dudi.getLoginCustomer().getId(), c.getId()));
		Assert.assertEquals(amountBefore, amountAfter);

	}

	//Get all customer's coupons
	@Test
	public void test_073_customerGetCoupons() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Collection <Coupon> coupons = avi.getAllPurchasedCoupons();
		System.out.println(coupons);

		int sizeInDb= couponRepo.findCustomerCoupons(avi.getLoginCustomer().getId()).size();				

		Assert.assertNotNull(coupons);
		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_074_customerGetCoupons() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Collection <Coupon> coupons = gabi.getAllPurchasedCoupons();
		System.out.println(coupons);

		int sizeInDb= couponRepo.findCustomerCoupons(gabi.getLoginCustomer().getId()).size();				

		Assert.assertNotNull(coupons);
		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_075_customerGetCoupons() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade dudi = (CustomerFacade) couponsystem.login("Dudi", "555", ClientType.CUSTOMER);
		Collection <Coupon> coupons = dudi.getAllPurchasedCoupons();
		System.out.println(coupons);

		//Cannot get coupons if the customer does not have any coupons
		Assert.assertNull(coupons);
	}


	//Get customer's coupons by type
	@Test
	public void test_076_customerGetCouponsByType() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Collection <Coupon> coupons = avi.getAllPurchasedCouponsByType(CouponType.CAMPING);
		System.out.println(coupons);

		int sizeInDb= couponRepo.findCustomerCouponsByType(avi.getLoginCustomer().getId(), CouponType.CAMPING).size();				

		Assert.assertNotNull(coupons);
		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_077_customerGetCouponsByType() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Collection <Coupon> coupons = gabi.getAllPurchasedCouponsByType(CouponType.TRAVELLING);
		System.out.println(coupons);

		int sizeInDb= couponRepo.findCustomerCouponsByType(gabi.getLoginCustomer().getId(), CouponType.TRAVELLING).size();				

		Assert.assertNotNull(coupons);
		Assert.assertEquals(sizeInDb, coupons.size());

	}


	@Test
	public void test_078_customerGetCouponsByType() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Collection <Coupon> coupons = gabi.getAllPurchasedCouponsByType(CouponType.ELECTRICITY);
		System.out.println(coupons);

		//Cannot get coupons if the customer does not have any coupons of this type
		Assert.assertNull(coupons);


	}

	//Get customer's coupons by price
	@Test
	public void test_079_customerGetCouponsByPrice() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade avi = (CustomerFacade) couponsystem.login("Avi", "111", ClientType.CUSTOMER);
		Collection <Coupon> coupons = avi.getAllPurchasedCouponsByPrice(500);
		System.out.println(coupons);

		int sizeInDb= couponRepo.findCustomerCouponsByMaxPrice(avi.getLoginCustomer().getId(), 500).size();				

		Assert.assertNotNull(coupons);
		Assert.assertEquals(sizeInDb, coupons.size());

	}


	@Test
	public void test_080_customerGetCouponsByPrice() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Collection <Coupon> coupons = gabi.getAllPurchasedCouponsByPrice(400);
		System.out.println(coupons);

		int sizeInDb= couponRepo.findCustomerCouponsByMaxPrice(gabi.getLoginCustomer().getId(), 400).size();				

		Assert.assertNotNull(coupons);
		Assert.assertEquals(sizeInDb, coupons.size());

	}

	@Test
	public void test_081_customerGetCouponsByPrice() throws WrongPasswordException, UserNotFoundException	{

		CustomerFacade gabi = (CustomerFacade) couponsystem.login("Gabi", "444", ClientType.CUSTOMER);
		Collection <Coupon> coupons = gabi.getAllPurchasedCouponsByPrice(10);
		System.out.println(coupons);

		//Cannot get coupons if the customer does not have any coupons under this price
		Assert.assertNull(coupons);
	}
	
	

}
