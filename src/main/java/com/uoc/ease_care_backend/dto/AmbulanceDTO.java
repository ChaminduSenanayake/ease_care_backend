package com.uoc.ease_care_backend.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AmbulanceDTO {
    private String ambulanceId;
    private String serviceProviderId;
    private String vehicleNumber;
    private String driverName;
    private String driverNIC;
    private String contactNumber;
    private String userName;
    private String password;
}
