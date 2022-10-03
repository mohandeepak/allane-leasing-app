package com.allane.leasing.ControllerTests;

import com.allane.leasing.controller.ContractController;
import com.allane.leasing.model.Contract;
import com.allane.leasing.model.Customer;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.service.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;


    private Contract contract1, contract2, contract3;

    @BeforeEach
    void setUp() {
        contract1  = new Contract(500L, 678, 100.0,
                new Customer(500L, "Mohan", "Prabhakaran", LocalDate.of(1994, 12, 24)),
                new Vehicle(500L, "Mercedes", "W105", "MUCH7865", 2022, 80.000));

        contract2  = new Contract(501L, 679, 200.0,
                new Customer(556L, "John", "Washington", LocalDate.of(1970, 3, 10)),
                new Vehicle(501L, "BMW", "X6", "BRTY1463", 2020, 50.000));

        contract3  = new Contract(502L, 680, 300.0,
                new Customer(557L, "Amy", "Jackson", LocalDate.of(1990, 9, 10)),
                new Vehicle(502L, "BMW", "x7", "BRTY1733", 2020, 50.000));
    }

    @Test
    void findAllContracts() throws Exception {
        when(contractService
                .findAllContracts())
                .thenReturn(List.of(contract1, contract2, contract3));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contracts/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].contractId").value(500L))
                .andExpect(jsonPath("$[1].contractId").value(501L))
                .andExpect(jsonPath("$[2].contractId").value(502L));
    }

    @Test
    void findContractById() throws Exception {
        when(contractService
                .findContractById(501L))
                .thenReturn(contract2);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contracts/{id}", 501L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractNumber").value(679));
    }


    @Test
    void saveContract() throws Exception {
        when(contractService.saveContract(any()))
                .thenReturn(contract3);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/contracts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(contract3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractNumber").value(680))
                .andExpect(jsonPath("$.monthlyRate").value(300));
    }


    @Test
    void updateContract() throws Exception {
        when(contractService.updateContract(any(), eq(500L)))
                .thenReturn(contract1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/contracts/{id}", 500L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(contract1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractId").value(500L))
                .andExpect(jsonPath("$.contractNumber").value(678))
                .andExpect(jsonPath("$.monthlyRate").value(100.0));
    }

    @Test
    void deleteContractById() throws Exception {
        when(contractService.saveContract(any()))
                .thenReturn(contract3);

        this.mockMvc.perform(delete("/contracts/{id}", 502L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
