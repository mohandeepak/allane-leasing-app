package com.allane.leasing.controller;


import com.allane.leasing.model.Contract;
import com.allane.leasing.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/")
    public ResponseEntity<List<Contract>> findAllContracts(){
        return new ResponseEntity<>(contractService.findAllContracts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> findContractById(@PathVariable("id") Long id){
        return new ResponseEntity<>(contractService.findContractById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Contract> saveContract(@RequestBody Contract contract){
        return new ResponseEntity<>(contractService.saveContract(contract), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@RequestBody Contract contract, @PathVariable("id") Long id){
        contractService.updateContract(contract, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable("id") Long id){
        contractService.deleteContractById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
