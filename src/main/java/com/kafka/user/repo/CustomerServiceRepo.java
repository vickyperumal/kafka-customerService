package com.kafka.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kafka.user.entity.CreateCustomerEntity;
import com.kafka.user.model.CustomerRegistrationModel;

@Repository
public interface CustomerServiceRepo extends MongoRepository<CustomerRegistrationModel, Long>{

}
