package com.uoc.ease_care_backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ServiceProviderDTO {
    private String serviceProviderName;
    private String hospitalName;
    private String address;
    private int contactNumber;
    private String email;
    private int registeredDate;
}
