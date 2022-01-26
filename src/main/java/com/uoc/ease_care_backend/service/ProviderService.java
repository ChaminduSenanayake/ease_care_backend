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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    private String collectionName = "ServiceProvider";
    private Logger logger = Logger.getLogger(String.valueOf(ProviderService.class));

    public boolean registerServiceProvider(ServiceProviderDTO dto) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String serviceProviderID = String.valueOf(getServiceProviders().size()+1);
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(serviceProviderID).set(dto);
        if(getServiceProvider(serviceProviderID) != null) {
            return true;
        }
        return false;
    }

    public List<ServiceProviderDTO> getServiceProviders(){
        List<ServiceProviderDTO> serviceProviderDTOS = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String collectionName = "ServiceProvider";
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(collectionName).get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException  | ExecutionException e) {
            logger.info("Can not register a new Service Provider");
        }
        for (QueryDocumentSnapshot document : documents) {
            ServiceProviderDTO dto=new ServiceProviderDTO();
            dto.setServiceProviderId(document.getId());
            dto.setServiceProviderName(document.get("serviceProviderName").toString());
            dto.setHospitalName(document.get("hospitalName").toString());
            dto.setAddress(document.get("address").toString());
            dto.setContactNumber(document.get("contactNumber").toString());
            dto.setEmail(document.get("email").toString());
            serviceProviderDTOS.add(dto);
        }
        return serviceProviderDTOS;
    }

    public ServiceProviderDTO getServiceProvider(String providerId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(collectionName).document(String.valueOf(providerId));
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = null;
        try {
            document = future.get();
            if(document.exists()) {
                ServiceProviderDTO dto=new ServiceProviderDTO();
                dto.setServiceProviderId(document.getId());
                dto.setServiceProviderName(document.get("serviceProviderName").toString());
                dto.setHospitalName(document.get("hospitalName").toString());
                dto.setAddress(document.get("address").toString());
                dto.setContactNumber(document.get("contactNumber").toString());
                dto.setEmail(document.get("email").toString());
                return dto;
            }
        } catch (InterruptedException  | ExecutionException e) {
            logger.info("Can not register a new Service Provider");
        }

        return null;

    }

    public boolean editServiceProvider(ServiceProviderDTO dto){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(String.valueOf(dto.getServiceProviderId())).set(dto);
        try {
            return (collectionsApiFuture.get().getUpdateTime().toString() == null) ? false :true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteServiceProvider(String serviceProviderID){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(getServiceProvider(serviceProviderID) != null) {
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(collectionName).document(serviceProviderID).delete();
            return true;
        }else {
            return false;
        }


    }

}
