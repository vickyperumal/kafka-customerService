package com.kafka.user.serviceimpl;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kafka.common.model.ProcessLoanDetails;
import com.kafka.common.model.ServiceResponse;
import com.kafka.user.entity.CustomerEntity;
import com.kafka.user.model.CustomerModel;
import com.kafka.user.repo.CustomerServiceRepo;
import com.kafka.user.service.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerServiceRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;

	@HystrixCommand(fallbackMethod = "enableFalseResponse")
	@Override
	public ServiceResponse<Object> insertCustomer(CustomerModel model){
		ServiceResponse<Object> response=null;
		try {
			Long customerId = ThreadLocalRandom.current().nextLong(1000, 9000);
			model.setCustomerId(customerId);
			CustomerEntity customerEntity=modelMapper.map(model, CustomerEntity.class);
			repo.save(customerEntity);
			log.info("Customer Id is {}", customerId);
			 response= buildServiceResponse("Registration Success and your customer Id is :" + customerId.toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			response=buildServiceResponse("Unable to Register", HttpStatus.INTERNAL_SERVER_ERROR);
			log.error("Error occured for during customer registration");
		}
		return response;

	}

	@Override
	public Integer retrieveCreditScoreOfCustomer(Long customerId) {
		try {
				Optional<CustomerEntity> customer = repo.findById(customerId);
				if (customer.isPresent()) {
					log.info("Credit Score for the customer Id {} is {}", customerId, customer.get().getCreditScore());
					return customer.get().getCreditScore();
				}
		} catch (Exception e) {
			log.error("Error occured during fetch creditscore");
		}
		return 0;
	}

	public void updateCreditScore(ProcessLoanDetails details) {
		if (details != null && !CollectionUtils.isEmpty(details.getLoanDetails())) {
			log.info("Updating creditScore for the customer Id {}", details.getLoanDetails().get(0).getCustomerId());
			Optional<CustomerEntity> model = repo.findById(details.getLoanDetails().get(0).getCustomerId());
			if (model.isPresent()) {
				CustomerEntity customer = model.get();
				customer.setCreditScore(details.getCreditScore());
				log.info("Final credit score is {}", details.getCreditScore());
				repo.save(customer);
			}
		}

	}

	@Override
	public ServiceResponse<Object> updateCustomerData(CustomerModel updateRequest) {

		Optional<CustomerEntity> customer = repo.findById(updateRequest.getCustomerId());
		ServiceResponse<Object> response=null;
		try {
			if (customer.isPresent()) {
				log.info("Updating details for the {} and customer id {}", updateRequest.getCountry(), updateRequest.getCustomerId());
				CustomerEntity customerDetails = customer.get();
				customerDetails.setAddress(updateRequest.getAddress());
				repo.save(customerDetails);
				 response= buildServiceResponse("Update Success",
							HttpStatus.OK);
				
			}
		} catch (Exception e) {
			log.error("Error occured during cusotmer updation");
			response= buildServiceResponse("Update Failure",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	private String enableFalseResponse() {
		log.info("Enabling false response");
		return "Please try after sometime";
	}

	private <T> ServiceResponse<T> buildServiceResponse(T body, HttpStatus status) {
		return new ServiceResponse<>(body, status.value());
	}

}
