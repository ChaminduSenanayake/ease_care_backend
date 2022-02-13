package com.uoc.ease_care_backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.uoc.ease_care_backend.dto.AmbulanceDTO;
import com.uoc.ease_care_backend.dto.ServiceProviderDTO;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class AmbulanceService {
    private String collectionName = "Ambulance";
    private Logger logger = Logger.getLogger(String.valueOf(ProviderService.class));

    public boolean registerAmbulance(AmbulanceDTO dto) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String ambulanceID = String.valueOf(generateID());
        dto.setAmbulanceId(ambulanceID);
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(ambulanceID).set(dto);
        if(getAmbulance(ambulanceID) != null) {
            return true;
        }
        return false;
    }

    public List<AmbulanceDTO> getAmbulances(){
        List<AmbulanceDTO> ambulances = new ArrayList<>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection(collectionName).get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException  | ExecutionException e) {
            logger.info("Can not get ambulances");
        }
        for (QueryDocumentSnapshot document : documents) {
            AmbulanceDTO dto=new AmbulanceDTO();
            dto.setAmbulanceId(document.get("ambulanceId").toString());
            dto.setServiceProviderId(document.get("serviceProviderId").toString());
            dto.setVehicleNumber(document.get("vehicleNumber").toString());
            dto.setDriverName(document.get("driverName").toString());
            dto.setDriverNIC(document.get("driverNIC").toString());
            dto.setContactNumber(document.get("contactNumber").toString());
            dto.setUserName(document.get("userName").toString());
            ambulances.add(dto);
        }
        return ambulances;
    }

    public List<AmbulanceDTO> getAmbulancesByProvider(String serviceProviderId){
        List<AmbulanceDTO> ambulances = new ArrayList<>();
        for (AmbulanceDTO dto : getAmbulances()) {
            if (dto.getServiceProviderId().equals(serviceProviderId)){
                ambulances.add(dto);
            }
        }
        return ambulances;
    }

    public AmbulanceDTO getAmbulance(String ambulaceId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(collectionName).document(ambulaceId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = null;
        try {
            document = future.get();
            if(document.exists()) {
                AmbulanceDTO dto=new AmbulanceDTO();
                dto.setAmbulanceId(document.get("ambulanceId").toString());
                dto.setServiceProviderId(document.get("serviceProviderId").toString());
                dto.setVehicleNumber(document.get("vehicleNumber").toString());
                dto.setDriverName(document.get("driverName").toString());
                dto.setDriverNIC(document.get("driverNIC").toString());
                dto.setContactNumber(document.get("contactNumber").toString());
                dto.setUserName(document.get("userName").toString());
                dto.setPassword(document.get("password").toString());
                return dto;
            }
        } catch (InterruptedException  | ExecutionException e) {
            logger.info("Can not retrieve ambulance ");
        }

        return null;

    }

    public boolean editAmbulance(AmbulanceDTO dto){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(dto.getAmbulanceId()).set(dto);
        try {
            return (collectionsApiFuture.get().getUpdateTime().toString() == null) ? false :true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAmbulance(String ambulanceId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(getAmbulance(ambulanceId) != null) {
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(collectionName).document(ambulanceId).delete();
            return true;
        }else {
            return false;
        }


    }

    public int generateID(){
        List<AmbulanceDTO> list =getAmbulances();
        int lastID;
        if(list.size()>0){
            lastID = Integer.parseInt(list.get(list.size()-1).getAmbulanceId());
        }else{
            lastID =0;
        }

        return lastID+1;
    }
}
