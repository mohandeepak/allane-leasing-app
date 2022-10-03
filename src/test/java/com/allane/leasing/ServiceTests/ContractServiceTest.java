package com.allane.leasing.ServiceTests;

import com.allane.leasing.exception.ContractException;
import com.allane.leasing.model.Contract;
import com.allane.leasing.model.Customer;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.ContractRepo;
import com.allane.leasing.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class ContractServiceTest {

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepo contractRepository;

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
    void findAllContracts() {
        List<Contract> list = new ArrayList<>();
        list.add(contract1);
        list.add(contract2);
        list.add(contract3);

        when(contractRepository.findAll()).thenReturn(list);
        List<Contract> result = contractService.findAllContracts();

        assertEquals(3, result.size());
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    void findContractById() {
        when(contractRepository.findById(contract1.getContractId())).thenReturn(Optional.of(contract1));
        Contract actualContract = contractService.findContractById(contract1.getContractId());
        assertEquals(500L, actualContract.getContractId());
        assertEquals(678, actualContract.getContractNumber());
        assertEquals(100.0, actualContract.getMonthlyRate());
        verify(contractRepository, times(1)).findById(actualContract.getContractId());
    }

    @Test
    void saveContract() {
        when(contractRepository.save(contract1)).thenReturn(contract1);
        when(contractRepository.findVehicleById(any())).thenReturn(null);
        Contract savedContract= contractService.saveContract(contract1);

        assertThat(savedContract).isEqualTo(contract1);
        verify(contractRepository, times(1)).save(contract1);
    }

    @Test
    void saveContractWithExistingVehicle() {
        Vehicle vehicle = new Vehicle(500L, "Mercedes", "W105", "MUCH7865", 2022, 80.000);
        when(contractRepository.save(contract1)).thenReturn(contract1);
        when(contractRepository.findVehicleById(any())).thenReturn(vehicle.getVehicleId());

        Exception exception = assertThrows(ContractException.class, () -> contractService.saveContract(contract1));;

        assertEquals("The vehicle already has an existing contract", exception.getMessage());

    }

    @Test
    void updateContractWithExistingVehicle() {
        Vehicle vehicle = new Vehicle(500L, "Mercedes", "W105", "MUCH7865", 2022, 80.000);
        when(contractRepository.save(contract1)).thenReturn(contract1);
        when(contractRepository.findVehicleById(any())).thenReturn(vehicle.getVehicleId());

        Exception exception = assertThrows(ContractException.class, () -> contractService.updateContract(contract1, anyLong()));

        assertEquals("The vehicle already has an existing contract", exception.getMessage());

    }
}