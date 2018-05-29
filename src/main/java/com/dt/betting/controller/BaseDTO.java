package com.dt.betting.controller;

import java.util.ArrayList;
import java.util.List;

public class BaseDTO {

	private List<String> errorMessages = new ArrayList<>();

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void addErrorMessage(String errorMessage) {
		if (errorMessage == null) {
			errorMessages = new ArrayList<>();
		}
		errorMessages.add(errorMessage);
	}
}
