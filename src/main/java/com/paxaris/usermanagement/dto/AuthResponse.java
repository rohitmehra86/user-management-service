package com.paxaris.usermanagement.dto;

import java.io.Serializable;

/**
 * @author Rohit Mehra
 */
public class AuthResponse implements Serializable {

	private String userName;
	private String token;
	private String refreshToken;

	public AuthResponse(String userName, String token, String refreshToken) {
		this.userName = userName;
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getUserName() {
		return userName;
	}

	public AuthResponse setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getToken() {
		return token;
	}

	public AuthResponse setToken(String token) {
		this.token = token;
		return this;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
