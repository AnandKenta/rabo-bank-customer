package com.rabobank.customer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.customer.exception.FormatException;
import com.rabobank.customer.record.Record;
import com.rabobank.customer.record.RecordResponse;
import com.rabobank.customer.service.ValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rabo")
@Api(value="Rabobank Customer Statement Processor")
public class StatementProcessController {

	private final Logger log = LoggerFactory.getLogger(StatementProcessController.class);
	
    @Autowired
    ValidationService validationService;

	@PostMapping(value = "/processor", consumes = { "application/json" }, produces = { "application/json" })
	@ApiOperation(value = "Customer statement pre validate")
	ResponseEntity<RecordResponse> processStatementValidation(@Validated @RequestBody List<Record> records)
			throws FormatException {
		log.info("Process Validation End point");
		return new ResponseEntity<>(validationService.validateRecordsFormat(records), HttpStatus.OK);

	}
}
