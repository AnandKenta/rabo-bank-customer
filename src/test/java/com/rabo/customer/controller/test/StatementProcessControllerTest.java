package com.rabo.customer.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.customer.constants.Constants;
import com.rabobank.customer.controller.StatementProcessController;
import com.rabobank.customer.record.Record;
import com.rabobank.customer.record.RecordResponse;
import com.rabobank.customer.service.ValidationService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class StatementProcessControllerTest {

	@Mock
	ValidationService validationService;

	@InjectMocks
	StatementProcessController statementProcessController;

	private MockMvc mockController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockController = MockMvcBuilders.standaloneSetup(statementProcessController).build();
	}

	@Test
	@DisplayName("Unit test success response")
	public void unitTestSuccessResponse() throws Exception {
		List<Record> records = new ArrayList<>();
		Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(40.2));
		records.add(record1);

		Record record2 = new Record();
		record2.setReference(2000);
		record2.setAccountNumber("NL99388838383");
		record2.setDescription("Test 1");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("+10.1"));
		record2.setEndBalance(BigDecimal.valueOf(20.2));
		records.add(record2);

		when(validationService.validateRecordsFormat(any())).thenReturn(new RecordResponse(Constants.SUCCESS));

		mockController
				.perform(MockMvcRequestBuilders.post("/rabo/processor")
						.content(new ObjectMapper().writeValueAsString(records)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.SUCCESS));
	}

	@Test
	@DisplayName("Unit test duplicate response")
	public void unitTestDuplicateResponse() throws Exception {
		List<Record> records = new ArrayList<>();
		Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(40.2));
		records.add(record1);

		Record record2 = new Record();
		record2.setReference(1000);
		record2.setAccountNumber("NL99388838383");
		record2.setDescription("Test 1");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("+10.1"));
		record2.setEndBalance(BigDecimal.valueOf(20.2));
		records.add(record2);

		when(validationService.validateRecordsFormat(any()))
				.thenReturn(new RecordResponse(Constants.DUPLICATE_REFERENCE));
		mockController
				.perform(MockMvcRequestBuilders.post("/rabo/processor")
						.content(new ObjectMapper().writeValueAsString(records)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.DUPLICATE_REFERENCE));
	}

	@Test
	@DisplayName("Unit test incorrect end balance response")
	public void unitTestIncorrectEndBalanceResponse() throws Exception {
		List<Record> records = new ArrayList<>();
		Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(40.2));
		records.add(record1);

		Record record2 = new Record();
		record2.setReference(2000);
		record2.setAccountNumber("NL99388838383");
		record2.setDescription("Test 1");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("-10.1"));
		record2.setEndBalance(BigDecimal.valueOf(10.2));
		records.add(record2);

		when(validationService.validateRecordsFormat(any()))
				.thenReturn(new RecordResponse(Constants.INCORRECT_END_BALANCE));
		mockController
				.perform(MockMvcRequestBuilders.post("/rabo/processor")
						.content(new ObjectMapper().writeValueAsString(records)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(Constants.INCORRECT_END_BALANCE));
	}

	@Test
	@DisplayName("Unit test dublicate & incorrect end balance response")
	public void unitTestDublicateIncorrectBalanceResponse() throws Exception {
		List<Record> records = new ArrayList<>();
		Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(40.2));
		records.add(record1);

		Record record2 = new Record();
		record2.setReference(1000);
		record2.setAccountNumber("NL99388838383");
		record2.setDescription("Test 1");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("-10.1"));
		record2.setEndBalance(BigDecimal.valueOf(10.2));
		records.add(record2);

		when(validationService.validateRecordsFormat(any()))
				.thenReturn(new RecordResponse(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE));
		mockController
				.perform(MockMvcRequestBuilders.post("/rabo/processor")
						.content(new ObjectMapper().writeValueAsString(records)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.result")
						.value(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE));
	}

	@Test
	@DisplayName("Unit test bad request response")
	public void unitTestBadRequestResponse() throws Exception {
		List<Record> records = new ArrayList<>();
		Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(40.2));
		records.add(record1);

		when(validationService.validateRecordsFormat(any())).thenReturn(new RecordResponse(Constants.BAD_REQUEST));
		mockController.perform(MockMvcRequestBuilders.post("/rabo/processor/test")
				.content(new ObjectMapper().writeValueAsString(records)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

}
