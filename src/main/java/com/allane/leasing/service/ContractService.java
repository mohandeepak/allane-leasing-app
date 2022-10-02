package com.allane.leasing.service;

import com.allane.leasing.exception.ContractException;
import com.allane.leasing.exception.ResourceNotFoundException;
import com.allane.leasing.model.Contract;
import com.allane.leasing.model.ContractOverview;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepo contractRepo;


    public List<Contract> findAllContracts(){
        return contractRepo.findAll();
    }

    public Contract findContractById(Long id){
        return contractRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contract with contract id"+ id + "was not found"));

    }

    public Contract saveContract(Contract contract){
        Vehicle vehicle = contract.getVehicle();
        if(contractRepo.findVehicleById(vehicle.getVehicleId())==null){
            return contractRepo.save(contract);
        }
       else{
            throw new ContractException("The vehicle already has an existing contract");
        }
    }

    public Contract updateContract(Contract contract, Long id){
        Vehicle vehicle = contract.getVehicle();
        Contract updatedContract =new Contract();
        if(contractRepo.findVehicleById(vehicle.getVehicleId())==null){
            updatedContract.setContractNumber(contract.getContractNumber());
            updatedContract.setCustomer(contract.getCustomer());
            updatedContract.setMonthlyRate(contract.getMonthlyRate());
            updatedContract.setVehicle(contract.getVehicle());
            contractRepo.save(updatedContract);
            return updatedContract;
        }
        else{
            throw new ContractException("The vehicle already has an existing contract");
        }
    }

    public void deleteContractById(Long id){
        contractRepo
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract with contract id"+ id + "was not found"));

        contractRepo.deleteById(id);
    }


    public List<ContractOverview> getContractOverview(){
        List<ContractOverview> contracts = new ArrayList<>();
        for (Contract contract: contractRepo.findAll()) {
            ContractOverview contractOverview = new ContractOverview(
                    contract.getContractNumber(),
                    contract.getCustomer().getFirstName(),
                    contract.getCustomer().getLastName(),
                    contract.getVehicle().getBrand(),
                    contract.getVehicle().getModel(),
                    contract.getVehicle().getYear(),
                    contract.getVehicle().getVin(),
                    contract.getMonthlyRate(),
                    contract.getVehicle().getPrice()
            );
            contracts.add(contractOverview);
        }
        return contracts;
    }

}
