package com.kafka.user.service;

import java.util.List;

import com.kafka.common.model.ServiceResponse;
import com.kafka.user.model.CustomerRegistrationModel;
import com.kafka.user.model.UpdateCustomerDetails;

public interface CustomerService {
	
	public ServiceResponse<Object> insertCustomer(CustomerRegistrationModel entity);


	public Integer retrieveCreditScoreOfCustomer(Long customerId);

	public void updateCustomerData(UpdateCustomerDetails entity);
	
	

}
