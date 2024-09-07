package com.brokerage.auth_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void testRegisterUser() {
		String url = "/api/auth/register";
		String requestJson = "{\"username\":\"admin\",\"password\":\"password\",\"role\":\"ADMIN\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		assert response.getStatusCode() == HttpStatus.OK;
	}

	@Test
	void testAuthenticateUser() {
		String url = "/api/auth/authenticate";
		String requestJson = "{\"username\":\"admin\",\"password\":\"password\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		assert response.getStatusCode() == HttpStatus.OK;
		assert response.getBody().contains("jwt");
	}
}

