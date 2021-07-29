package com.kafka.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.common.model.ServiceResponse;
import com.kafka.user.model.CustomerRegistrationModel;
import com.kafka.user.model.UpdateCustomerDetails;
import com.kafka.user.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * Accepts the customer service request 
 * @author vignesh
 * @version 0.0.1
 */

@RestController
@Slf4j
@RequestMapping("/v1/customerService")
public class CustomerServiceController {

	@Autowired
	private CustomerService service;

	@PostMapping("/createCustomer")
	public ServiceResponse<Object> createCustomer(@RequestBody CustomerRegistrationModel entity) {
		
			log.info("Registering new Customer for {}",entity.getName());
			ServiceResponse<Object> response=service.insertCustomer(entity);
			return response;
		
	}

	@GetMapping("/getCustomerCreditScore/{customerId}")
	public Integer getCustomeCreditScore(@PathVariable("customerId") Long customerId) {
		log.info("Retrieiving creditScore through customer Id {}",customerId);
		return service.retrieveCreditScoreOfCustomer(customerId);

	}
	
	@PutMapping("/updateCustomerDetails")
	public String updateCustomerDetails(@RequestBody UpdateCustomerDetails entity ) {
		try {
			service.updateCustomerData(entity);
		}
		catch(Exception e) {
			
		}
		return "success";
	}
}
