package com.kafka.user.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kafka.common.model.ProcessLoanDetails;
import com.kafka.common.model.ServiceResponse;
import com.kafka.user.model.CustomerRegistrationModel;
import com.kafka.user.model.UpdateCustomerDetails;
import com.kafka.user.repo.CustomerServiceRepo;
import com.kafka.user.service.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerServiceRepo repo;

	@HystrixCommand(fallbackMethod = "enableFalseResponse")
	@Override
	public ServiceResponse<Object> insertCustomer(CustomerRegistrationModel customerEntity) {
		try {
			Long customerId = ThreadLocalRandom.current().nextLong(1000, 9000);
			customerEntity.setCustomerId(customerId);
			repo.save(customerEntity);
			log.info("Customer Id is {}", customerId);
			return buildServiceResponse("Registration Sucess and your customer Id is :" + customerId.toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			buildServiceResponse("Unable to Register", HttpStatus.OK);
			log.error("Error occured for during customer registration");
		}
		return null;

	}

	@Override
	public Integer retrieveCreditScoreOfCustomer(Long customerId) {
		try {
			if (customerId != null) {
				Optional<CustomerRegistrationModel> customer = repo.findById(customerId);
				if (customer.isPresent()) {
					log.info("Credit Score for the customer Id {} is {}", customerId, customer.get().getCreditScore());
					return customer.get().getCreditScore();
				}
			}
		} catch (Exception e) {
			log.error("Error occured during fetch creditscore");
		}
		return 0;
	}

	@SuppressWarnings("null")
	public void updateCreditScore(ProcessLoanDetails details) {
		if (details != null || !CollectionUtils.isEmpty(details.getLoanDetails())) {
			log.info("Updating creditScore for the customer Id {}", details.getLoanDetails().get(0).getCustomerId());
			Optional<CustomerRegistrationModel> model = repo.findById(details.getLoanDetails().get(0).getCustomerId());
			if (model.isPresent()) {
				CustomerRegistrationModel customer = model.get();
				customer.setCreditScore(details.getCreditScore());
				log.info("Final credit score is {}", details.getCreditScore());
				repo.save(customer);
			}
		}

	}

	@Override
	public void updateCustomerData(UpdateCustomerDetails entity) {

		Optional<CustomerRegistrationModel> customer = repo.findById(entity.getCustomerId());
		try {
			if (customer.isPresent()) {
				log.info("Updating details for the {} and customer id {}", entity.getCountry(), entity.getCustomerId());
				CustomerRegistrationModel customerDetails = customer.get();
				customerDetails.setAddress(entity.getAddress());
				repo.save(customerDetails);
			}
		} catch (Exception e) {
			log.error("Error occured during cusotmer updation");
		}
	}

	public String enableFalseResponse() {
		log.info("Enabling false response");
		return "Please try after sometime";
	}

	private <T> ServiceResponse<T> buildServiceResponse(T body, HttpStatus status) {
		return new ServiceResponse<>(body, status.value());
	}

}
