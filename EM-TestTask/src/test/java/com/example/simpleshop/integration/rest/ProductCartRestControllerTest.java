package com.example.simpleshop.integration.rest;

import com.example.simpleshop.entity.ProductCart;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.enums.Role;
import com.example.simpleshop.exception.ProductCartNotFoundException;
import com.example.simpleshop.integration.IntegrationTestBase;
import com.example.simpleshop.repository.UserRepository;
import com.example.simpleshop.service.ProductCartService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ProductCartRestControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    private final ProductCartService productCartService;

    @Test
    @DisplayName("create product cart")
    @WithMockUser(username = "don@gmail.com")
    void checkCreateProductCartMethod() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/purchases/carts/create"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.createDate").exists(),
                        MockMvcResultMatchers.jsonPath("$.productList").exists(),
                        MockMvcResultMatchers.jsonPath("$.userReadDto").exists()
                );
    }

    @Test
    @DisplayName("postpone product")
    @WithMockUser(username = "don@gmail.com")
    void checkPostponeProductMethod() throws Exception {
        productCartService.createCart(() -> "don@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/purchases/postpone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productName\": \"Temp\", \"productAmount\": \"2\" }")
                )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$.id").exists(),
                        MockMvcResultMatchers.jsonPath("$.createDate").exists(),
                        MockMvcResultMatchers.jsonPath("$.productList").isNotEmpty(),
                        MockMvcResultMatchers.jsonPath("$.userReadDto").exists()
                );

    }

    @Test
    @DisplayName("get exception if product cart does not exist")
    @WithMockUser(username = "don@gmail.com")
    void shouldExceptionIfCartDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/purchases/postpone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productName\": \"Temp\", \"productAmount\": \"2\" }")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        result -> assertTrue(result.getResolvedException() instanceof ProductCartNotFoundException),
                        result -> assertEquals("Product cart is not found! Create!", result.getResolvedException().getLocalizedMessage()));

    }


}