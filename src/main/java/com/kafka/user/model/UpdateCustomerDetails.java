package com.kafka.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerDetails {

	private Long customerId;
	private String address;
	private String state;
	private String country;
	//@Email(message = "Email should be valid")
	private String email;
	private String pan;
	 //@Size(min=0,max=10)
	private String contactNo;
	@Override
	public String toString() {
		return "UpdateCustomerDetails [customerId=" + customerId + ", address=" + address + ", state=" + state
				+ ", country=" + country + ", email=" + email + ", pan=" + pan + ", contactNo=" + contactNo + "]";
	}
	
	
	
}
