package com.example.demo.facades;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.WrongPasswordException;

public interface CouponClientFacade {
	/**
	 * 
	 * Login
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws WrongPasswordException
	 * @throws UserNotFoundException
	 */
	CouponClientFacade login(String name, String password) throws WrongPasswordException, UserNotFoundException;

}
