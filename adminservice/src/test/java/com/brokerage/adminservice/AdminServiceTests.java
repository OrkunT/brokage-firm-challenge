package com.brokerage.adminservice;

import com.brokerage.adminservice.model.Asset;
import com.brokerage.adminservice.model.Order;
import com.brokerage.adminservice.repository.AssetRepository;
import com.brokerage.adminservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminServiceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private OrderRepository orderRepository;

	@BeforeEach
	public void setUp() {
		// Insert data programmatically into Asset table
		assetRepository.saveAll(Arrays.asList(
				new Asset(1L, "TRY", 10000.0, 10000.0), // User has 10000 TRY
				new Asset(1L, "ETH", 10.0, 10.0),
				new Asset(1L, "BTC", 5.0, 5.0)
		));

		// Insert data programmatically into Order table
		orderRepository.saveAll(Arrays.asList(
				new Order(1L, "ETH", "BUY", 2.0, 2000.0, "PENDING", LocalDateTime.now()), // User wants to buy 2 ETH at 2000 TRY each
				new Order(1L, "BTC", "SELL", 1.0, 30000.0, "PENDING", LocalDateTime.now()) // User wants to sell 1 BTC at 30000 TRY
		));

		// Debug statements to verify initial setup
		System.out.println("Initial Assets:");
		assetRepository.findAll().forEach(asset ->
				System.out.println(asset.getAssetName() + " Size: " + asset.getSize() + ", Usable Size: " + asset.getUsableSize())
		);

		System.out.println("Initial Orders:");
		orderRepository.findAll().forEach(order ->
				System.out.println(order.getAssetName() + " Order: " + order.getOrderSide() + ", Size: " + order.getSize() + ", Price: " + order.getPrice() + ", Status: " + order.getStatus())
		);
	}


	@Test
	public void testMatchOrders() throws Exception {
		// Perform the matchOrders request
		mockMvc.perform(post("/api/admin/matchOrders"))
				.andExpect(status().isNoContent());

		// Verify the asset sizes and statuses after matching orders
		Asset tryAsset = assetRepository.findByAssetNameAndCustomerId("TRY", 1L).orElseThrow(() -> new RuntimeException("Asset not found"));
		Asset ethAsset = assetRepository.findByAssetNameAndCustomerId("ETH", 1L).orElseThrow(() -> new RuntimeException("Asset not found"));
		Asset btcAsset = assetRepository.findByAssetNameAndCustomerId("BTC", 1L).orElseThrow(() -> new RuntimeException("Asset not found"));

		// Debug statements to print the actual values
		System.out.println("TRY Asset Size: " + tryAsset.getSize());
		System.out.println("ETH Asset Size: " + ethAsset.getSize());
		System.out.println("BTC Asset Size: " + btcAsset.getSize());

		// User buys 2 ETH at 2000 TRY each, so 4000 TRY is deducted
		assertThat(ethAsset.getSize()).isEqualTo(12.0); // 10 + 2 (BUY order)

		// User sells 1 BTC at 30000 TRY, so 30000 TRY is added
		assertThat(btcAsset.getSize()).isEqualTo(4.0); // 5 - 1 (SELL order)
		assertThat(tryAsset.getSize()).isEqualTo(36000.0); // 6000 + 30000

		Order ethOrder = orderRepository.findByAssetNameAndCustomerId("ETH", 1L).get(0);
		Order btcOrder = orderRepository.findByAssetNameAndCustomerId("BTC", 1L).get(0);

		assertThat(ethOrder.getStatus()).isEqualTo("MATCHED");
		assertThat(btcOrder.getStatus()).isEqualTo("MATCHED");
	}

}
