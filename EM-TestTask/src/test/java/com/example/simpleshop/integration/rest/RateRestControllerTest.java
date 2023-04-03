package com.example.simpleshop.integration.rest;

import com.example.simpleshop.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class RateRestControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @WithMockUser(username = "era@gmail.com", authorities = {"ADMIN"})
    void checkAddRateMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": \"1\", \"rate\": \"4\"}"
                        )
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().is2xxSuccessful(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.userReadDto").exists(),
                        MockMvcResultMatchers.jsonPath("$.productReadDto").exists()

                );
    }

}