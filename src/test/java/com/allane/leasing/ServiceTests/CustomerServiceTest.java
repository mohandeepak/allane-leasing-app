package com.allane.leasing.ServiceTests;

import com.allane.leasing.model.Customer;
import com.allane.leasing.repository.CustomerRepo;
import com.allane.leasing.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepo customerRepository;

    private Customer customer1, customer2, customer3;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(555L, "Mohan", "Prabhakaran", LocalDate.of(1994, 12, 24));
        customer2 = new Customer(556L, "John", "Washington", LocalDate.of(1970, 3, 10));
        customer3 = new Customer(557L, "Amy", "Jackson", LocalDate.of(1990, 9, 10));
    }

    @Test
    void findAllCustomer() {
        List<Customer> list = new ArrayList<>();
        list.add(customer1);
        list.add(customer2);
        list.add(customer3);

        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> result = customerService.findAllCustomers();

        assertEquals(3, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findCustomerById() {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.of(customer1));
        Customer actualCustomer = customerService.findCustomerById(customer1.getCustomerId());

        assertEquals(555L, actualCustomer.getCustomerId());
        assertEquals("Mohan", actualCustomer.getFirstName());
        assertEquals("Prabhakaran", actualCustomer.getLastName());
        verify(customerRepository, times(1)).findById(actualCustomer.getCustomerId());
    }

    @Test
    void saveCustomer() {
        when(customerRepository.save(customer1)).thenReturn(customer1);

        assertThat(customerService.saveCustomer(customer1)).isEqualTo(customer1);
        verify(customerRepository, times(1)).save(customer1);
    }

    @Test
    void updateCustomer() {
        customer1.setFirstName("Mohan");
        customer1.setLastName("P");
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer1));
        when(customerRepository.save(customer1)).thenReturn(customer1);
        Customer areaUpdated = customerService.updateCustomer(customer1, 555L);
        assertEquals("Mohan", areaUpdated.getFirstName());
        assertEquals("P", areaUpdated.getLastName());
        verify(customerRepository, times(1)).save(customer1);
    }


}
