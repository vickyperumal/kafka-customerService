package com.kafka.user.service;

import com.kafka.common.model.ServiceResponse;
import com.kafka.user.model.CustomerModel;
import com.kafka.user.model.UpdateCustomerDetails;

public interface CustomerService {
	
	public ServiceResponse<Object> insertCustomer(CustomerModel model);


	public Integer retrieveCreditScoreOfCustomer(Long customerId);

	public ServiceResponse<Object> updateCustomerData(CustomerModel entity);
	
	

}
