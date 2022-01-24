package com.uoc.ease_care_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceProviderDTO {
    private int serviceProviderId;
    private String serviceProviderName;
    private String hospitalName;
    private String address;
    private String contactNumber;
    private String email;
    private int registeredDate;
}
