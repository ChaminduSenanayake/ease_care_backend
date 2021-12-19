package com.uoc.ease_care_backend.service;

import com.uoc.ease_care_backend.dto.ServiceProviderDTO;
import com.uoc.ease_care_backend.entity.ServiceProvider;
import com.uoc.ease_care_backend.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService {
    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    public boolean registerServiceProvider(ServiceProviderDTO dto){
        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.setServiceProviderId(serviceProviderRepository.findAll().size()+1);
        serviceProvider.setServiceProviderName(dto.getServiceProviderName());
        serviceProvider.setHospitalName(dto.getHospitalName());
        serviceProvider.setAddress(dto.getAddress());
        serviceProvider.setContactNumber(dto.getContactNumber());
        serviceProvider.setEmail(dto.getEmail());
        ServiceProvider result=serviceProviderRepository.save(serviceProvider);
        if (result!=null){
            return true;
        }
        return false;
    }

    public List<ServiceProviderDTO> getServiceProviders(){
        List<ServiceProvider> results=serviceProviderRepository.findAll();
        List<ServiceProviderDTO> serviceProviderDTOS=new ArrayList<ServiceProviderDTO>();
        if (results!=null){
            for (ServiceProvider serviceProvider:results){
                ServiceProviderDTO dto=new ServiceProviderDTO();
                dto.setServiceProviderName(serviceProvider.getServiceProviderName());
                dto.setHospitalName(serviceProvider.getHospitalName());
                dto.setAddress(serviceProvider.getAddress());
                dto.setContactNumber(serviceProvider.getContactNumber());
                dto.setEmail(serviceProvider.getEmail());
                serviceProviderDTOS.add(dto);
            }
        }
        return serviceProviderDTOS;
    }
}
