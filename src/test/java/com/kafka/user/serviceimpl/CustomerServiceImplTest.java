package com.kafka.user.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.kafka.common.model.LoanDetails;
import com.kafka.common.model.ProcessLoanDetails;
import com.kafka.common.model.ServiceResponse;
import com.kafka.user.entity.CustomerEntity;
import com.kafka.user.model.CustomerModel;
import com.kafka.user.repo.CustomerServiceRepo;

@SuppressWarnings("rawtypes")
class CustomerServiceImplTest {
	
	@InjectMocks
	private CustomerServiceImpl service;
	
	@Mock
	private CustomerServiceRepo repo;
	
	private CustomerModel request;
	
	private CustomerEntity customerEntity;
	
	@Mock
	private ModelMapper modelMapper;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		
		request = new CustomerModel();
		customerEntity = new CustomerEntity();
		request.setAccountType("savings");
		request.setAddress("address");
		request.setContactNo("12345");
		request.setCountry("INDIA");
		request.setDob(LocalDate.now().minusDays(4));
		request.setEmail("test@gmail.com");
		request.setName("Messi");
		request.setPan("ARQNDNF");
	}

	@Test
	void testInsertCustomer() {
		when(modelMapper.map(Mockito.any(CustomerModel.class),Mockito.eq(CustomerEntity.class))).thenReturn(customerEntity);
		when(repo.save(Mockito.any(CustomerEntity.class))).thenReturn(customerEntity);
		ServiceResponse<Object> result=service.insertCustomer(request);
		assertNotNull(result);
		assertNotNull(result.getBody());
		assertEquals(200, result.getStatus());
		verify(repo, times(1)).save(Mockito.any(CustomerEntity.class));
	}
	
	@Test
	void testInsertCustomerError() {
		when(modelMapper.map(Mockito.any(CustomerModel.class),Mockito.eq(CustomerEntity.class))).thenReturn(customerEntity);
		when(repo.save(Mockito.any(CustomerEntity.class))).thenThrow(NullPointerException.class);
		ServiceResponse result=service.insertCustomer(request);
		assertNotNull(result);
		assertNotNull(result.getBody());
		assertEquals("Unable to Register",result.getBody());
		assertEquals(500, result.getStatus());
		verify(repo, times(1)).save(Mockito.any(CustomerEntity.class));
	}

	@Test
	void testRetrieveCreditScoreOfCustomer() {
		when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(customerEntity));
		ServiceResponse result=service.retrieveCreditScoreOfCustomer(1234L);
		assertNotNull(result);
		assertNotEquals(0, result);
		verify(repo, times(1)).findById(Mockito.anyLong());
	}
	
	
	@Test
	void testRetrieveCreditScoreOfCustomerNotPresent() {
		when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		ServiceResponse result=service.retrieveCreditScoreOfCustomer(1234L);
		assertNotNull(result);
		//assertEquals(0, result);
		verify(repo, times(1)).findById(Mockito.anyLong());
	}
	

	@Test
	void testUpdateCreditScore() {
		List<LoanDetails> details = Arrays
				.asList(new LoanDetails("loanType", 1000L,4321L, "loanDate", 5.00, 24, 1234L, 100000L, 5));
		ProcessLoanDetails processloandetails=ProcessLoanDetails.builder().loanDetails(details).creditScore(5).build();
		when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(customerEntity));
		when(repo.save(Mockito.any(CustomerEntity.class))).thenReturn(customerEntity);
		service.updateCreditScore(processloandetails);
		
		verify(repo, times(1)).save(Mockito.any(CustomerEntity.class));
		verify(repo, times(1)).findById(Mockito.anyLong());
		
	}

	@Test
	void testUpdateCustomerData() {
		request.setCustomerId(1234L);
		when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(customerEntity));
		when(repo.save(Mockito.any(CustomerEntity.class))).thenReturn(customerEntity);
		ServiceResponse<Object> result=service.updateCustomerData(request);
		assertNotNull(result);
		assertNotNull(result.getBody());
		assertEquals("Update Success",result.getBody());
		assertEquals(200, result.getStatus());
		verify(repo, times(1)).save(Mockito.any(CustomerEntity.class));
		verify(repo, times(1)).findById(Mockito.anyLong());
	}
	
	@Test
	void testUpdateCustomerDataNotPresent() {
		request.setCustomerId(1234L);
		when(repo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		ServiceResponse<Object> result=service.updateCustomerData(request);
		assertNull(result);
		verify(repo, times(1)).findById(Mockito.anyLong());
	}

	@Test
	void testUpdateCustomerDataError() {
		request.setCustomerId(1234L);
		when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(customerEntity));
		when(repo.save(Mockito.any(CustomerEntity.class))).thenThrow(NullPointerException.class);
		ServiceResponse<Object> result=service.updateCustomerData(request);
		assertNotNull(result);
		assertNotNull(result.getBody());
		assertEquals("Update Failure",result.getBody());
		assertEquals(500, result.getStatus());
		verify(repo, times(1)).save(Mockito.any(CustomerEntity.class));
		verify(repo, times(1)).findById(Mockito.anyLong());
	}
	

}
