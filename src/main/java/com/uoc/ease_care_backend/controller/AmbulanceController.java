package com.uoc.ease_care_backend.controller;

import com.uoc.ease_care_backend.dto.AmbulanceDTO;
import com.uoc.ease_care_backend.dto.ServiceProviderDTO;
import com.uoc.ease_care_backend.service.AmbulanceService;
import com.uoc.ease_care_backend.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ambulance")
@CrossOrigin(origins = "*")
public class AmbulanceController {
    @Autowired
    private AmbulanceService ambulanceService;
    @PostMapping("/register")
    public boolean registerAmbulance(@RequestBody AmbulanceDTO ambulance){
        return ambulanceService.registerAmbulance(ambulance);
    }

    @GetMapping("/getAll")
    public List<AmbulanceDTO> getAmbulances(){
        return ambulanceService.getAmbulances();
    }

    @GetMapping("/getServiceProvider/{serviceProviderId}")
    public AmbulanceDTO getAmbulance(@PathVariable String ambulanceId){
        return ambulanceService.getAmbulance(ambulanceId);
    }

    @PutMapping("/edit")
    public boolean editAmbulance(@RequestBody AmbulanceDTO ambulance){
        return ambulanceService.editAmbulance(ambulance);
    }

    @DeleteMapping("/delete/{serviceProviderId}")
    public boolean deleteAmbulance(@PathVariable String ambulanceId){
        return ambulanceService.deleteAmbulance(ambulanceId);
    }
}