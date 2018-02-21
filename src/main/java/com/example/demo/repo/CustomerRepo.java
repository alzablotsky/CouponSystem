package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.*;

public interface CustomerRepo extends CrudRepository<Customer, Long>{

	List<Customer> findCustomerByCustomerName(String customerName);
	List<Customer> findCustomerByCustomerNameAndPassword(String customerName, String password);
	List<Customer> findCustomerById (long id);
	List<Customer> findCustomerByIdAndCustomerName (long id, String customerName);


	
	/**
	 * 
	 * Find customer by id
	 * 
	 * @param id
	 * @return
	 */
	@Query("SELECT c FROM CUSTOMERS c WHERE c.id = :id") 
	Customer findById(@Param("id") long id);
	
	
	/**
	 * 
	 * Find customer by name
	 * 
	 * @param customerName
	 * @return
	 */
	@Query("SELECT c FROM CUSTOMERS c WHERE c.customerName = :customerName") 
	Customer findByCustomerName(@Param("customerName") String customerName);
	
	/**
	 * 
	 * Find customer by name and password
	 * 
	 * @param customerName
	 * @param password
	 * @return
	 */
	@Query("SELECT c FROM CUSTOMERS c WHERE c.customerName = :customerName AND c.password = :password") 
	Customer findByCustomerNameAndPassword(@Param("customerName") String customerName, @Param("password") String password);
	
	
	

}
