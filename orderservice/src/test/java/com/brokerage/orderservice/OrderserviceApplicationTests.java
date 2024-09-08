package com.brokerage.orderservice;

import com.brokerage.orderservice.model.Order;
import com.brokerage.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

		// Mock the service layer to return the mockOrder
		when(orderService.createOrder(any(Order.class))).thenReturn(mockOrder);

		// Perform the POST request and verify the response
		mockMvc.perform(post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderJson))
				.andExpect(status().isOk())
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
		// Create a mock Order object
		Order mockOrder = new Order();
		mockOrder.setCustomerId(1L);
		mockOrder.setAssetName("AAPL");
		mockOrder.setOrderSide("BUY");
		mockOrder.setSize(10.0);
		mockOrder.setPrice(150.0);
		mockOrder.setCreateDate(LocalDateTime.of(2023, 1, 1, 0, 0));

		// Create a list of mock orders
		List<Order> mockOrders = Collections.singletonList(mockOrder);

		// Mock the service layer to return the list of mock orders
		when(orderService.listOrders(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(mockOrders);

		// Perform the GET request and verify the response
		mockMvc.perform(get("/api/orders")
						.param("customerId", "1")
						.param("startDate", "2023-01-01T00:00:00")
						.param("endDate", "2023-12-31T23:59:59"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].customerId").value(1))
				.andExpect(jsonPath("$[0].assetName").value("AAPL"))
				.andExpect(jsonPath("$[0].orderSide").value("BUY"))
				.andExpect(jsonPath("$[0].size").value(10))
				.andExpect(jsonPath("$[0].price").value(150.0));
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	void testDeleteOrder() throws Exception {
		// Mock the Order object that the service layer will return
		Order mockOrder = new Order();
		mockOrder.setId(1L);
		mockOrder.setCustomerId(1L);
		mockOrder.setAssetName("AAPL");
		mockOrder.setOrderSide("BUY");
		mockOrder.setSize(10.0);
		mockOrder.setPrice(150.0);
		mockOrder.setStatus("PENDING");
		mockOrder.setCreateDate(LocalDateTime.now());

		// Mock the service layer to return the mockOrder
		when(orderService.findOrderById(1L)).thenReturn(mockOrder);

		// Perform the DELETE request
		mockMvc.perform(delete("/api/orders/1"))
				.andExpect(status().isNoContent());

		// Verify that the status of the order is set to "CANCELED"
		verify(orderService).updateOrder(argThat(order ->
				order.getId().equals(1L) && "CANCELED".equals(order.getStatus())
		));
	}

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

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	void testGenericException() throws Exception {
		when(orderService.createOrder(any(Order.class))).thenThrow(new RuntimeException("Unexpected error"));

		String orderJson = "{\"customerId\":1,\"assetName\":\"AAPL\",\"orderSide\":\"BUY\",\"size\":10.0,\"price\":150.0,\"status\":\"PENDING\",\"createDate\":\"2024-09-07T06:56:36\"}";

		mockMvc.perform(post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderJson))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
				.andExpect(jsonPath("$.message").value("Unexpected error"))
				.andExpect(jsonPath("$.errors").value("error occurred"));
	}



}

