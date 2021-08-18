package com.kafka.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.common.model.ServiceResponse;
import com.kafka.user.model.CustomerModel;
import com.kafka.user.service.CustomerService;

@ExtendWith(SpringExtension.class)
class CustomerServiceControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private CustomerService service;
	
	private CustomerModel request;
	
	private ServiceResponse<Object> response;
	
	private ObjectMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerServiceController(service)).build();
		mapper=new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		response=new ServiceResponse<Object>();
		request = new CustomerModel();
		request.setAccountType("savings");
		request.setAddress("address");
		request.setContactNo("1234567891");
		request.setCountry("INDIA");
		request.setDob(LocalDate.now().minusDays(2));
		request.setEmail("test@gmail.com");
		request.setName("Messi");
		request.setPan("ARQNDNFasd");
	}

	@Test
	void testCreateCustomer() throws JsonProcessingException, Exception {
		response.setBody("Registration Success");
		when(service.insertCustomer(Mockito.any(CustomerModel.class))).thenReturn(response);
		MvcResult result = this.mockMvc.perform(post("/v1/customerService/createCustomer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse());
		assertTrue(result.getResponse().getContentAsString().contains("Success"));
		verify(service, times(1)).insertCustomer(Mockito.any(CustomerModel.class));
	}
	
	@Test
	void testCreateCustomeError() throws JsonProcessingException, Exception {
		request.setContactNo(null);
		 this.mockMvc.perform(post("/v1/customerService/createCustomer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andReturn();
	
	}

	@Test
	void testGetCustomeCreditScore() throws JsonProcessingException, Exception {
		response.setBody(100);
		response.setStatus(200);
		when(service.retrieveCreditScoreOfCustomer(Mockito.any(Long.class))).thenReturn(response);
		MvcResult result = this.mockMvc.perform(get("/v1/customerService/getCustomerCreditScore/1234"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse());
		verify(service, times(1)).retrieveCreditScoreOfCustomer(Mockito.any(Long.class));
	}
	
	@Test
	void testGetCustomeCreditScoreError() throws JsonProcessingException, Exception {
		response.setBody(null);
		response.setStatus(500);
		when(service.retrieveCreditScoreOfCustomer(Mockito.any(Long.class))).thenReturn(response);
		MvcResult result = this.mockMvc.perform(get("/v1/customerService/getCustomerCreditScore/123456"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse());
		verify(service, times(1)).retrieveCreditScoreOfCustomer(Mockito.any(Long.class));
	}

	@Test
	void testUpdateCustomerDetails() throws JsonProcessingException, Exception {
		request.setCustomerId(1234L);
		response.setBody("Update Success");
		System.out.println(mapper.writeValueAsString(request));
		when(service.updateCustomerData(Mockito.any(CustomerModel.class))).thenReturn(response);
		MvcResult result = this.mockMvc.perform(put("/v1/customerService/updateCustomerDetails")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andReturn();
		
		assertNotNull(result.getResponse());
		assertTrue(result.getResponse().getContentAsString().contains("Success"));
		verify(service, times(1)).updateCustomerData(Mockito.any(CustomerModel.class));
	}
	}

