package com.example.demo.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CouponClientFacade;
import com.example.demo.facades.CustomerFacade;

@Component
@Scope("singleton")
public class CouponSystem {


	@Autowired
	AdminFacade adminFacade;

	@Autowired
	CompanyFacade companyFacade;

	@Autowired
	CustomerFacade customerFacade;


	public CouponClientFacade login(String name, String password, ClientType clientType)
	{
		try {	
			switch (clientType)
			{
			case ADMIN:
				AdminFacade myAdminFacade = (AdminFacade) adminFacade.login(name, password);
				return myAdminFacade;

			case COMPANY:
				CompanyFacade myCompanyFacade = (CompanyFacade) companyFacade.login(name, password);
				return myCompanyFacade;
				
				
			case CUSTOMER: 
				CustomerFacade myCustomerFacade= (CustomerFacade) customerFacade.login(name, password);
				return myCustomerFacade;
				
			}
		}
		catch (WrongPasswordException e) {
			System.err.println(e.getMessage());
		}
		catch (UserNotFoundException e) {
			System.err.println(e.getMessage());
		}
		return null;			

	}
}
