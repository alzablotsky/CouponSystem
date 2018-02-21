package com.example.demo.exceptions;

public class WrongPasswordException extends Exception  {
	
	public WrongPasswordException (String message){
	super(message);
	}
}
