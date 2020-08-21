package com.rabo.customer.service.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabobank.customer.constants.Constants;
import com.rabobank.customer.record.Record;
import com.rabobank.customer.record.RecordResponse;
import com.rabobank.customer.service.impl.ValidationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationSerivceImplTest {
	
    @InjectMocks
    ValidationServiceImpl validationServiceImpl;
    
    
    @Test
    @DisplayName("Success validation")
    public void validateRecordsFormatSuccess() {
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
		record2.setDescription("Test 2");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("+10.1"));
		record2.setEndBalance(BigDecimal.valueOf(20.2));
		records.add(record2);

        RecordResponse recordResponse = validationServiceImpl.validateRecordsFormat(records);
        Assert.assertEquals(recordResponse.getResult(), Constants.SUCCESS);

    }
    
    @Test
    @DisplayName("Duplicate validation")
    public void validateRecordsFormatDuplicate() {
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
		record2.setDescription("Test 2");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("+10.1"));
		record2.setEndBalance(BigDecimal.valueOf(20.2));
		records.add(record2);

        RecordResponse recordResponse = validationServiceImpl.validateRecordsFormat(records);
        Assert.assertEquals(recordResponse.getResult(), Constants.DUPLICATE_REFERENCE);

    }
    
    @Test
    @DisplayName("Duplicate Incorrect balance validation")
    public void validateRecordsFormatDuplicateIncorrectBalance() {
    	List<Record> records = new ArrayList<>();
    	Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(10.2));
		records.add(record1);

		Record record2 = new Record();
		record2.setReference(1000);
		record2.setAccountNumber("NL99388838383");
		record2.setDescription("Test 2");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("+10.1"));
		record2.setEndBalance(BigDecimal.valueOf(10.2));
		records.add(record2);

        RecordResponse recordResponse = validationServiceImpl.validateRecordsFormat(records);
        Assert.assertEquals(recordResponse.getResult(), Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);

    }
    
    @Test
    @DisplayName("Incorrect balance validation")
    public void validateRecordsFormatIncorrectBalance() {
    	List<Record> records = new ArrayList<>();
    	Record record1 = new Record();
		record1.setReference(1000);
		record1.setAccountNumber("NLJE9849934MND");
		record1.setDescription("Test 1");
		record1.setStartBalance(BigDecimal.valueOf(20.1));
		record1.setMutation(new BigDecimal("+20.1"));
		record1.setEndBalance(BigDecimal.valueOf(10.2));
		records.add(record1);

		Record record2 = new Record();
		record2.setReference(2000);
		record2.setAccountNumber("NL99388838383");
		record2.setDescription("Test 2");
		record2.setStartBalance(BigDecimal.valueOf(10.1));
		record2.setMutation(new BigDecimal("+10.1"));
		record2.setEndBalance(BigDecimal.valueOf(10.2));
		records.add(record2);

        RecordResponse recordResponse = validationServiceImpl.validateRecordsFormat(records);
        Assert.assertEquals(recordResponse.getResult(), Constants.INCORRECT_END_BALANCE);

    }

}
