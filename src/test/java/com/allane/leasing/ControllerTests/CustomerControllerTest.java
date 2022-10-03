package com.allane.leasing.ControllerTests;

import com.allane.leasing.controller.CustomerController;
import com.allane.leasing.model.Customer;
import com.allane.leasing.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private Customer customer1, customer2, customer3;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(555L, "Mohan", "Prabhakaran", LocalDate.of(1994, 12, 24));
        customer2 = new Customer(556L, "John", "Washington", LocalDate.of(1970, 3, 10));
        customer3 = new Customer(557L, "Amy", "Jackson", LocalDate.of(1990, 9, 10));
    }

    @Test
    void findAllCustomers() throws Exception {
        when(customerService
                .findAllCustomers())
                .thenReturn(List.of(customer1, customer2, customer3));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].customerId").value(555L))
                .andExpect(jsonPath("$[1].customerId").value(556L))
                .andExpect(jsonPath("$[2].customerId").value(557L));
    }

    @Test
    void findCustomerById() throws Exception {
        when(customerService
                .findCustomerById(557L))
                .thenReturn(customer3);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/{id}", 557L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Amy"))
                .andExpect(jsonPath("$.lastName").value("Jackson"));
    }

    @Test
    void saveCustomer() throws Exception {
        when(customerService.saveCustomer(any()))
                .thenReturn(customer1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(customer1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(555L))
                .andExpect(jsonPath("$.firstName").value("Mohan"))
                .andExpect(jsonPath("$.lastName").value("Prabhakaran"));
    }

    @Test
    void updateCustomer() throws Exception {
        when(customerService.updateCustomer(any(), eq(556L)))
                .thenReturn(customer2);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/customers/{id}", 556L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(customer2))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Washington"));
    }

    @Test
    void deleteCustomerById() throws Exception {
        when(customerService.saveCustomer(any()))
                .thenReturn(customer3);

        this.mockMvc.perform(delete("/customers/{id}", 557L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}