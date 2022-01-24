package com.uoc.ease_care_backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.uoc.ease_care_backend.dto.ServiceProviderDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    private String collectionName = "ServiceProvider";

    public boolean registerServiceProvider(ServiceProviderDTO dto) {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        try {
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(getServiceProviders().size()+1+"").set(dto);
            System.out.println(collectionsApiFuture.get().getUpdateTime().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<ServiceProviderDTO> getServiceProviders(){
        List<ServiceProviderDTO> serviceProviderDTOS = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String collectionName = "ServiceProvider";
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(collectionName).get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (QueryDocumentSnapshot document : documents) {
            ServiceProviderDTO dto=new ServiceProviderDTO();
            dto.setServiceProviderId(Integer.parseInt(document.getId()));
            dto.setServiceProviderName(document.get("serviceProviderName").toString());
            dto.setHospitalName(document.get("hospitalName").toString());
            dto.setAddress(document.get("address").toString());
            dto.setContactNumber(document.get("contactNumber").toString());
            dto.setEmail(document.get("email").toString());
            serviceProviderDTOS.add(dto);
        }
        return serviceProviderDTOS;
    }

    public ServiceProviderDTO getServiceProvider(int providerId){
        return null;
    }

    public boolean editServiceProvider(ServiceProviderDTO dto){
//
        return false;
    }

    public boolean deleteServiceProvider(int providerId){
//
        return false;
    }

}
