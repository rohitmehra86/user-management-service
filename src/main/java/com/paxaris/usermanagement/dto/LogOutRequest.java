package com.paxaris.usermanagement.dto;

public class LogOutRequest {
	private String userName;
	private Integer tenantOrClientId;

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
