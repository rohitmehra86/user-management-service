package com.paxaris.usermanagement.dto;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
	private String refreshToken;
	private String userName;
	private Integer tenantOrClientId;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTenantOrClientId() {
		return tenantOrClientId;
	}

	public void setTenantOrClientId(Integer tenantOrClientId) {
		this.tenantOrClientId = tenantOrClientId;
	}

}
