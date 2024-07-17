package com.paxaris.usermanagement.tenant.config;

public class UsernameNotFoundException extends RuntimeException {
	String erorMessage;

	public UsernameNotFoundException(String erorMessage) {
		super();
		this.erorMessage = erorMessage;
	}

}
