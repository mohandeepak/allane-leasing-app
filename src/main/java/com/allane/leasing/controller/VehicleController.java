package com.allane.leasing.controller;

import com.allane.leasing.model.Vehicle;
import com.allane.leasing.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> findAllVehicles(){
        return new ResponseEntity<>(vehicleService.findAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable("id") Long id){
        return new ResponseEntity<>( vehicleService.findVehicleById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody Vehicle vehicle){
        return new ResponseEntity<>(vehicleService.saveVehicle(vehicle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("id") Long id){
        return new ResponseEntity<>( vehicleService.updateVehicle(vehicle, id),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") Long id){
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
