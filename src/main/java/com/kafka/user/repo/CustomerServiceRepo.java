package com.kafka.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kafka.user.entity.CustomerEntity;

@Repository
public interface CustomerServiceRepo extends MongoRepository<CustomerEntity, Long>{

}
