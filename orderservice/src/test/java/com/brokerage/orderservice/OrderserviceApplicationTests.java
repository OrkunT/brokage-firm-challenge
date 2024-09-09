package com.brokerage.orderservice;

import com.brokerage.orderservice.domain.model.Order;
import com.brokerage.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Test
	void contextLoads() {
	}



	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	void testCreateOrder() throws Exception {
		String orderJson = "{\"customerId\":1,\"assetName\":\"AAPL\",\"orderSide\":\"BUY\",\"size\":10.0,\"price\":150.0,\"status\":\"PENDING\",\"createDate\":\"2024-09-07T06:56:36\"}";

		// Mock the Order object that the service layer will return
		Order mockOrder = new Order();
		mockOrder.setCustomerId(1L);
		mockOrder.setAssetName("AAPL");
		mockOrder.setOrderSide("BUY");
		mockOrder.setSize(10.0);
		mockOrder.setPrice(150.0);
		mockOrder.setStatus("PENDING");
		mockOrder.setCreateDate(LocalDateTime.parse("2024-09-07T06:56:36"));  // Ensure createDate is set

		// Log the mock order object
		System.out.println("Mock Order: " + mockOrder);

		// Mock the service layer to return the mockOrder wrapped in a CompletableFuture
		when(orderService.createOrder(any(Order.class))).thenReturn(CompletableFuture.completedFuture(mockOrder));

		// Perform the POST request and capture the result
		MvcResult result = mockMvc.perform(post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderJson))
				.andExpect(status().isOk())
				.andReturn();

		// Log the response content
		String responseContent = result.getResponse().getContentAsString();
		System.out.println("Response Content: " + responseContent);

		// Verify the response content
		mockMvc.perform(asyncDispatch(result))
				.andExpect(jsonPath("$.customerId").value(1))
				.andExpect(jsonPath("$.assetName").value("AAPL"))
				.andExpect(jsonPath("$.orderSide").value("BUY"))
				.andExpect(jsonPath("$.size").value(10.0))
				.andExpect(jsonPath("$.price").value(150.0))
				.andExpect(jsonPath("$.status").value("PENDING"))
				.andExpect(jsonPath("$.createDate").value("2024-09-07T06:56:36"));  // Verify createDate
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	void testGetOrders() throws Exception {
		// Mock the list of orders that the service layer will return
		Order mockOrder = new Order();
		mockOrder.setCustomerId(1L);
		mockOrder.setAssetName("AAPL");
		mockOrder.setOrderSide("BUY");
		mockOrder.setSize(10.0);
		mockOrder.setPrice(150.0);
		mockOrder.setStatus("PENDING");
		mockOrder.setCreateDate(LocalDateTime.parse("2024-09-07T06:56:36"));  // Ensure createDate is set

		List<Order> mockOrderList = Collections.singletonList(mockOrder);

		// Log the mock order list
		System.out.println("Mock Order List: " + mockOrderList);

		// Mock the service layer to return the mockOrderList wrapped in a CompletableFuture
		when(orderService.listOrders(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
				.thenReturn(CompletableFuture.completedFuture(mockOrderList));

		// Perform the GET request and capture the result
		MvcResult result = mockMvc.perform(get("/api/orders")
						.param("customerId", "1")
						.param("startDate", "2023-01-01T00:00:00")
						.param("endDate", "2023-12-31T23:59:59"))
				.andExpect(status().isOk())
				.andReturn();

		// Log the result status
		System.out.println("Result Status: " + result.getResponse().getStatus());

		// Log the response content
		String responseContent = result.getResponse().getContentAsString();
		System.out.println("Response Content: " + responseContent);

		// Verify the response content
		mockMvc.perform(asyncDispatch(result))
				.andExpect(jsonPath("$[0].customerId").value(1))
				.andExpect(jsonPath("$[0].assetName").value("AAPL"))
				.andExpect(jsonPath("$[0].orderSide").value("BUY"))
				.andExpect(jsonPath("$[0].size").value(10.0))
				.andExpect(jsonPath("$[0].price").value(150.0))
				.andExpect(jsonPath("$[0].status").value("PENDING"))
				.andExpect(jsonPath("$[0].createDate").value("2024-09-07T06:56:36"));  // Verify createDate
	}

/*	@Test
	void testOrderSerialization() throws Exception {
		Order order = new Order();
		order.setCustomerId(1L);
		order.setAssetName("AAPL");
		order.setOrderSide("BUY");
		order.setSize(10.0);
		order.setPrice(150.0);
		order.setStatus("PENDING");
		order.setCreateDate(LocalDateTime.parse("2024-09-07T06:56:36"));

		ObjectMapper objectMapper = new ObjectMapper();
		String orderJson = objectMapper.writeValueAsString(order);
		System.out.println("Serialized Order JSON: " + orderJson);
	}*/


	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	void testCreateOrderValidationException() throws Exception {
		String invalidOrderJson = "{\"customerId\":1,\"assetName\":\"AAPL\",\"orderSide\":\"BUY\",\"size\":10.0,\"price\":150.0,\"status\":\"PENDING\"}";

		mockMvc.perform(post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidOrderJson))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.errors").isArray());
	}


}

