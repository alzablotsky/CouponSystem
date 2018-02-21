package com.example.demo.repo;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.*;


public interface CompanyRepo extends CrudRepository<Company, Long> {
	
	List<Company> findCompanyByCompanyName(String companyName);
	List<Company> findCompanyByCompanyNameAndPassword(String companyName, String password);
	List<Company> findCompanyById(long id);
	List<Company> findCompanyByIdAndCompanyName(long id, String companyName);
	
	
	/**
	 * 
	 * Find company by id
	 * 
	 * @param id
	 * @return
	 */
	@Query("SELECT c FROM COMPANIES c WHERE c.id = :id") 
	Company findById(@Param("id") long id);

	
	
	/**
	 * 
	 * Find company by name
	 * 
	 * @param companyName
	 * @return
	 */
	@Query("SELECT c FROM COMPANIES c WHERE c.companyName = :companyName") 
	Company findByName(@Param("companyName") String companyName);

	
	/**
	 * 
	 * Find company by name and password
	 * 
	 * @param companyName
	 * @param password
	 * @return
	 */
	@Query("SELECT c FROM COMPANIES c WHERE c.companyName = :companyName AND c.password = :password") 
	Company findByNameAndPwd(@Param("companyName") String companyName, @Param ("password") String password);

}
