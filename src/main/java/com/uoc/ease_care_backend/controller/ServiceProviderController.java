package com.uoc.ease_care_backend.controller;

import com.uoc.ease_care_backend.dto.ServiceProviderDTO;
import com.uoc.ease_care_backend.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/serviceProvider")
@CrossOrigin(origins = "*")
public class ServiceProviderController {
    @Autowired
    private ProviderService providerService;
    @PostMapping("/register")
    public boolean registerServiceProvider(@RequestBody ServiceProviderDTO serviceProvider){
        return providerService.registerServiceProvider(serviceProvider);
    }

    @GetMapping("/getAll")
    public List<ServiceProviderDTO> getServiceProviders(){
        return providerService.getServiceProviders();
    }

    @GetMapping("/getServiceProvider/{serviceProviderId}")
    public ServiceProviderDTO getServiceProvider(@PathVariable String serviceProviderId){
        return providerService.getServiceProvider(serviceProviderId);
    }

    @PutMapping("/edit")
    public boolean editServiceProvider(@RequestBody ServiceProviderDTO serviceProvider){
        return providerService.editServiceProvider(serviceProvider);
    }

    @DeleteMapping("/delete/{serviceProviderId}")
    public boolean deleteServiceProvider(@PathVariable String serviceProviderId){
        return providerService.deleteServiceProvider(serviceProviderId);
    }
}
