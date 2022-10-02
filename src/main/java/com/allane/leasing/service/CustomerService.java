package com.allane.leasing.service;

import com.allane.leasing.exception.ResourceNotFoundException;
import com.allane.leasing.model.Customer;
import com.allane.leasing.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public List<Customer> findAllCustomers(){
        return customerRepo.findAll();
    }

    public Customer findCustomerById(Long id){
        return customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with cust id"+ id + "was not found"));
    }

    public Customer saveCustomer(Customer customer){
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Customer customer, Long id){
            customerRepo.findById(id).ifPresent(updatedCustomer -> {
                updatedCustomer.setFirstName(customer.getFirstName());
                updatedCustomer.setLastName(customer.getLastName());
                updatedCustomer.setBirthDate(customer.getBirthDate());
                customerRepo.save(updatedCustomer);
            });

        return customer;
    }

    public void deleteCustomerById(Long id){
        customerRepo
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with cust id"+ id + "was not found"));

        customerRepo.deleteById(id);
    }

}
