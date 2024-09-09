package com.brokerage.transactionservice;

import com.brokerage.transactionservice.repository.TransactionRepository;
import com.brokerage.transactionservice.service.TransactionService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Autowired
	private MockMvc mockMvc;


	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionService transactionService;

	/*@Test
	@WithMockUser(username = "user", roles = {"USER"})
	void testDepositMoneyCreatesTRYAssetIfNotFound() {
		Long customerId = 1L;
		Double amount = 100.0;

		// Mock the asset repository to return an empty result
		when(assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")).thenReturn(Optional.empty());

		// Mock the asset repository to save the new asset
		Asset newAsset = new Asset();
		newAsset.setCustomerId(customerId);
		newAsset.setAssetName("TRY");
		newAsset.setSize(0.0);
		newAsset.setUsableSize(0.0);
		when(assetRepository.save(any(Asset.class))).thenReturn(newAsset);

		// Mock the transaction repository to save the transaction
		Transaction transaction = new Transaction();
		transaction.setCustomerId(customerId);
		transaction.setAmount(amount);
		transaction.setType("DEPOSIT");
		transaction.setTransactionDate(LocalDateTime.now());
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		// Perform the deposit
		Transaction result = transactionService.depositMoney(customerId, amount);

		// Verify the asset repository save method was called twice
		verify(assetRepository, times(2)).save(any(Asset.class));

		// Verify the transaction repository save method was called
		verify(transactionRepository).save(any(Transaction.class));

		// Assert the result
		assertEquals(customerId, result.getCustomerId());
		assertEquals(amount, result.getAmount());
		assertEquals("DEPOSIT", result.getType());
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void testWithdraw() throws Exception {
		// Perform a deposit operation
		mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/deposit")
						.param("customerId", "1")
						.param("amount", "1000"))
				.andExpect(status().isOk());

		// Perform the withdrawal operation
		mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/withdraw")
						.param("customerId", "1")
						.param("amount", "500")
						.param("iban", "TR123456789012345678901234"))
				.andExpect(status().isOk());
	}*/
}
