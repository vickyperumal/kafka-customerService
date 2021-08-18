package com.kafka.user.service;

import com.kafka.common.model.ServiceResponse;

import com.kafka.user.model.CustomerModel;
@SuppressWarnings("rawtypes")
public interface CustomerService {
	
	public ServiceResponse insertCustomer(CustomerModel model);


	public ServiceResponse retrieveCreditScoreOfCustomer(Long customerId);

	
	public ServiceResponse updateCustomerData(CustomerModel entity);
	
	

}
