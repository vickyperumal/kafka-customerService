package com.kafka.user.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document(collection="Customer_Details")
@Data
public class CustomerEntity {
	private String name;
	private String userName;
	//@NotNull(message = "password should not be empty")
    //@Size(min = 6, max = 8, message = "")
	private String password;
	private String address;
	private String state;
	private String country;
	//@Email(message = "Email should be valid")
	private String email;
	private String pan;
	 //@Size(min=0,max=10)
	private String contactNo;
	 @Id
	 private Long customerId;
	 
	
	
	@NotNull
	@Past
	@DateTimeFormat(pattern="dd/mm/yyyy")
	private Date dob;
	private String accountType;
	
	
	private Integer creditScore=100;
	
	
	
	
	
	
	
	
	

}
