package com.rabobank.customer.record;

import java.util.HashSet;
import java.util.Set;

public class RecordResponse {
	private String result;
	private Set<ErrorRecord> errorRecords;

	public RecordResponse(String result) {
		super();
		this.result = result;
		this.errorRecords = new HashSet<>();
	}

	public RecordResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Set<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}

	public void setErrorRecords(Set<ErrorRecord> errorRecords) {
		this.errorRecords = errorRecords;
	}

	@Override
	public String toString() {
		return "RecordResponse [result=" + result + ", errorRecords=" + errorRecords + "]";
	}
}
