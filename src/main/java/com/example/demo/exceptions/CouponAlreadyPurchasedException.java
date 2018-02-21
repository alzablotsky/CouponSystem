package com.example.demo.exceptions;

public class CouponAlreadyPurchasedException extends Exception {
	
	public CouponAlreadyPurchasedException (String message){
		super(message);
	}

}
