package fr.kubys.leekscriptv4.api;

public class ApiException extends Exception {

	public ApiException(String method, String url, String params, String errorMessage) {
		super(String.format("Error %s while calling API using %s (url=%s, params=%s)", errorMessage, method, url, params));
	}
}
