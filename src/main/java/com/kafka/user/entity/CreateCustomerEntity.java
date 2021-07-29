package com.kafka.user.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "user")
@Data
public class CreateCustomerEntity {
	@Id
	private String id;
	private List<String> names;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<String> getNames() {
		return names;
	}


	public void setNames(List<String> names) {
		this.names = names;
	}


	@Override
	public String toString() {
		return "CreateCustomerEntity [id=" + id + ", names=" + names + "]";
	}
	
	
	
	
	
	

}
