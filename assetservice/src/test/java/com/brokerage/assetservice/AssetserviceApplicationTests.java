package com.brokerage.assetservice;

import com.brokerage.assetservice.model.Asset;
import com.brokerage.assetservice.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class AssetserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssetRepository assetRepository;

    @BeforeEach
    public void setUp() {
        // Insert data programmatically
        assetRepository.saveAll(Arrays.asList(
                new Asset("ETH", 1L),
                new Asset("BTC", 1L),
                new Asset("Asset 3", 1L),
                new Asset("Asset 4", 1L),
                new Asset("Asset 5", 1L)
        ));
    }

    @Test
    public void testGetAssets() throws Exception {
        mockMvc.perform(get("/api/assets")
                        .param("customerId", "1")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "assetName")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].assetName").exists());
    }
}
