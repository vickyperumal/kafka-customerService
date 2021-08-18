package com.kafka.user.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document(collection = "Customer_Details")
@Data
public class CustomerEntity {
	@Id
	private Long customerId;
	private String name;
	private String userName;
	private String password;
	private String address;
	private String state;
	private String country;
	private String email;
	private String pan;
	private String contactNo;
	
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private LocalDate dob;
	private String accountType;

	private Integer creditScore = 100;

}
