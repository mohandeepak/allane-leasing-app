package com.allane.leasing.service;

import com.allane.leasing.exception.ResourceNotFoundException;
import com.allane.leasing.model.Vehicle;
import com.allane.leasing.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;


    public List<Vehicle> findAllVehicles(){
        return vehicleRepo.findAll();
    }

    public Vehicle findVehicleById(Long id){
        return vehicleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle with vehicle id"+ id + "was not found"));
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        return vehicleRepo.save(vehicle);
    }

    public Vehicle updateVehicle(Vehicle vehicle, Long id){
        vehicleRepo.findById(id).ifPresent(updatedVehicle -> {
            updatedVehicle.setBrand(vehicle.getBrand());
            updatedVehicle.setModel(vehicle.getModel());
            updatedVehicle.setPrice(vehicle.getPrice());
            updatedVehicle.setVin(vehicle.getVin());
            updatedVehicle.setYear(vehicle.getYear());
            vehicleRepo.save(updatedVehicle);
        });
        return vehicle;
    }

    public void deleteVehicleById(Long id){
        vehicleRepo
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with vehicle id"+ id + "was not found"));

        vehicleRepo.deleteById(id);
    }
}
