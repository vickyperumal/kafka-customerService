package com.kafka.user.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.kafka.common.model.ProcessLoanDetails;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;

private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@KafkaListener(id="consumer-1",groupId="group-id-1",topics = "loan-topic")
		public void receiveMessage(@Payload ProcessLoanDetails user, @Header(KafkaHeaders.OFFSET) long offset){
		logger.info("1:Received [{}] with offset [{}]", user, offset);
		 
			customerServiceImpl.updateCreditScore(user);	
		
	
}


}
