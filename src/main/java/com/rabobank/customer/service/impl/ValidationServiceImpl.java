package com.rabobank.customer.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabobank.customer.constants.Constants;
import com.rabobank.customer.record.ErrorRecord;
import com.rabobank.customer.record.Record;
import com.rabobank.customer.record.RecordResponse;
import com.rabobank.customer.service.ValidationService;

@Component
public class ValidationServiceImpl implements ValidationService {
	private static final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);

	@Override
	public RecordResponse validateRecordsFormat(List<Record> records) {
		RecordResponse response = new RecordResponse(Constants.SUCCESS);
		Set<ErrorRecord> duplicateErrorRecordSet = getDuplicateErrorRecords(
				records.stream().filter(o -> Collections.frequency(records, o) > 1).collect(Collectors.toList()));
		Set<ErrorRecord> balanceErrorRecordSet = getBalanceErrorRedord(records);

		if (duplicateErrorRecordSet.size() > 0 && balanceErrorRecordSet.size() > 0) {
			log.info(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
			response.setResult(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
			response.setErrorRecords(Stream.of(duplicateErrorRecordSet, balanceErrorRecordSet).flatMap(x -> x.stream())
					.collect(Collectors.toSet()));
		} else if (duplicateErrorRecordSet.size() > 0) {
			log.info(Constants.DUPLICATE_REFERENCE);
			response.setErrorRecords(duplicateErrorRecordSet);
			response.setResult(Constants.DUPLICATE_REFERENCE);
		} else if (balanceErrorRecordSet.size() > 0) {
			log.info(Constants.INCORRECT_END_BALANCE);
			response.setResult(Constants.INCORRECT_END_BALANCE);
			response.setErrorRecords(balanceErrorRecordSet);
		}
		return response;
	}

	private Set<ErrorRecord> getDuplicateErrorRecords(List<Record> errorDublicateList) {
		Set<ErrorRecord> errorRecordSet = new HashSet<>();
		errorDublicateList.forEach(record -> {
			ErrorRecord eRecord = new ErrorRecord();
			eRecord.setAccountNumber(record.getAccountNumber());
			eRecord.setReference(record.getReference());
			errorRecordSet.add(eRecord);
		});
		return errorRecordSet;
	}

	private Set<ErrorRecord> getBalanceErrorRedord(List<Record> records) {
		Set<ErrorRecord> errorRecordSet = new HashSet<>();
		records.forEach(record -> {
			if (!(record.getStartBalance().add(record.getMutation())).stripTrailingZeros()
					.equals(record.getEndBalance().stripTrailingZeros())) {
				ErrorRecord eRecord = new ErrorRecord();
				eRecord.setAccountNumber(record.getAccountNumber());
				eRecord.setReference(record.getReference());
				errorRecordSet.add(eRecord);
			}
		});
		return errorRecordSet;
	}

}
