package com.uoc.ease_care_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceProviderDTO {
    private String serviceProviderId;
    private String serviceProviderName;
    private String hospitalName;
    private String address;
    private String contactNumber;
    private String email;
    private PaymentStatus paymentStatus;
    private Long registeredDate;

    public enum PaymentStatus{
        INACTIVE,ACTIVE
    }
}
