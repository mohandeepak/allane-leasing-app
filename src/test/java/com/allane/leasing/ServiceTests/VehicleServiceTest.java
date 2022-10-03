package com.allane.leasing.ServiceTests;

import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.VehicleRepo;
import com.allane.leasing.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.extension.ExtendWith;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepo vehicleRepo;


    private Vehicle vehicle1, vehicle2, vehicle3;

    @BeforeEach
    void setUp() {
        vehicle1 = new Vehicle(500L, "Mercedes", "W105", "MUCH7865", 2022, 80.000);
        vehicle2 = new Vehicle(501L, "BMW", "X6", "BRTY1463", 2020, 50.000);
        vehicle3 = new Vehicle(502L, "BMW", "x7", "BRTY1733", 2020, 50.000);
    }

    @Test
    void findAllCustomer() {
        List<Vehicle> list = new ArrayList<>();
        list.add(vehicle1);
        list.add(vehicle2);
        list.add(vehicle3);

        when(vehicleRepo.findAll()).thenReturn(list);
        List<Vehicle> result = vehicleService.findAllVehicles();

        assertEquals(3, result.size());
        verify(vehicleRepo, times(1)).findAll();
    }

    @Test
    void findCustomerById() {
        when(vehicleRepo.findById(vehicle1.getVehicleId())).thenReturn(Optional.of(vehicle1));
        Vehicle actualVehicle = vehicleService.findVehicleById(vehicle1.getVehicleId());

        assertEquals(500L, actualVehicle.getVehicleId());
        assertEquals("Mercedes", actualVehicle.getBrand());
        assertEquals("W105", actualVehicle.getModel());
        assertEquals("MUCH7865", actualVehicle.getVin());
        assertEquals(2022, actualVehicle.getYear());
        assertEquals(80.000, actualVehicle.getPrice());
        verify(vehicleRepo, times(1)).findById(actualVehicle.getVehicleId());
    }

    @Test
    void saveCustomer() {
        when(vehicleRepo.save(vehicle1)).thenReturn(vehicle1);
        Vehicle result = vehicleService.saveVehicle(vehicle1);
        assertThat(result).isEqualTo(vehicle1);
        verify(vehicleRepo, times(1)).save(vehicle1);
    }


    @Test
    void updateCustomer() {
        vehicle1.setBrand("Toyota");
        vehicle1.setModel("xyz");

        when(vehicleRepo.findById(any())).thenReturn(Optional.of(vehicle1));
        when(vehicleRepo.save(vehicle1)).thenReturn(vehicle1);
        Vehicle vehicleUpdated = vehicleService.updateVehicle(vehicle1, anyLong());
        assertEquals("Toyota", vehicleUpdated.getBrand());
        assertEquals("xyz", vehicleUpdated.getModel());
        verify(vehicleRepo, times(1)).save(vehicle1);
    }


}
