package com.uoc.ease_care_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmbulanceRegisterDTO {
    private String userId;
    private String serviceProviderId;
    private String email;
    private String number;
    private String driverNIC;
    private String name;
    private String contactNumber;
    private String password;
    private String ambulanceCharge;
    private boolean isFree;
    private String status;
}
