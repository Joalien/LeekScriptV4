package fr.kubys.leekscriptv4.api.dto;

import java.util.Map;

public class GenericResponse {

	private boolean success;
	private String error;
	private Map<String, Object> result;

	public boolean isSuccess() {
		return success;
	}

	public String getError() {
		return error;
	}

	public Map<String, Object> getResult() {
		return result;
	}
}
