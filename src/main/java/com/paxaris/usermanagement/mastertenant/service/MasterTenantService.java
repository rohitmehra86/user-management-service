package com.paxaris.usermanagement.mastertenant.service;

import com.paxaris.usermanagement.mastertenant.entity.MasterTenant;

/**
 * @author Rohit Mehra
 */
public interface MasterTenantService {

    MasterTenant findByClientId(Integer clientId);
}
