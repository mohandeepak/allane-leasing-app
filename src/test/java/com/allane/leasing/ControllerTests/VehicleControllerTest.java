package com.allane.leasing.ControllerTests;

import com.allane.leasing.controller.VehicleController;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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


@ExtendWith(SpringExtension.class)
@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private Vehicle vehicle1, vehicle2, vehicle3;

    @BeforeEach
    void setUp() {
        vehicle1 = new Vehicle(500L, "Mercedes", "W105", "MUCH7865", 2022, 80.000);
        vehicle2 = new Vehicle(501L, "BMW", "X6", "BRTY1463", 2020, 50.000);
        vehicle3 = new Vehicle(502L, "BMW", "x7", "BRTY1733", 2020, 50.000);
    }

    @Test
    void findAllVehicles() throws Exception {
        when(vehicleService
                .findAllVehicles())
                .thenReturn(List.of(vehicle1, vehicle2, vehicle3));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/vehicles/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].vehicleId").value(500L))
                .andExpect(jsonPath("$[1].vehicleId").value(501L))
                .andExpect(jsonPath("$[2].vehicleId").value(502L));
    }

    @Test
    void findVehicleById() throws Exception {
        when(vehicleService
                .findVehicleById(500L))
                .thenReturn(vehicle1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/vehicles/{id}", 500L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(500L))
                .andExpect(jsonPath("$.brand").value("Mercedes"))
                .andExpect(jsonPath("$.model").value("W105"))
                .andExpect(jsonPath("$.year").value(2022));
    }

    @Test
    void saveVehicle() throws Exception {
        when(vehicleService.saveVehicle(any()))
                .thenReturn(vehicle1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/vehicles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(vehicle1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleId").value(500L))
                .andExpect(jsonPath("$.brand").value("Mercedes"))
                .andExpect(jsonPath("$.model").value("W105"));
    }



    @Test
    void updateVehicle() throws Exception {
        when(vehicleService.updateVehicle(any(), eq(501L)))
                .thenReturn(vehicle2);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/vehicles/{id}", 501L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(vehicle2))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(501L))
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X6"));
    }

    @Test
    void deleteVehicleById() throws Exception {
        when(vehicleService.saveVehicle(any()))
                .thenReturn(vehicle3);

        this.mockMvc.perform(delete("/vehicles/{id}", 502L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

