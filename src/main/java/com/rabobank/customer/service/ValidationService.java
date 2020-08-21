package com.rabobank.customer.service;

import java.util.List;

import com.rabobank.customer.record.Record;
import com.rabobank.customer.record.RecordResponse;


public interface ValidationService {
	RecordResponse validateRecordsFormat(List<Record> records);
}
