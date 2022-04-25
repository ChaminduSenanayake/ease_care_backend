package com.uoc.ease_care_backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.DeleteUsersResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.uoc.ease_care_backend.dto.AmbulanceDTO;
import com.uoc.ease_care_backend.dto.AmbulanceRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class AmbulanceService {
    private String collectionName = "Ambulance";
    private Logger logger = Logger.getLogger(String.valueOf(ProviderService.class));

    public boolean registerAmbulance(AmbulanceRegisterDTO dto) {
        UserRecord.CreateRequest req = new UserRecord.CreateRequest();
        req.setEmail(dto.getEmail());
        req.setPassword(dto.getPassword());
        dto.setStatus("Activate");
        dto.setFree(true);
        try {
            UserRecord user = FirebaseAuth.getInstance().createUser(req);
            Firestore dbFirestore = FirestoreClient.getFirestore();
            String ambulanceID = String.valueOf(user.getUid());
            dto.setUserId(ambulanceID);
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(ambulanceID).set(dto);
            if(getAmbulance(ambulanceID) != null) {
                return true;
            }
            return false;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
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
            dto.setUserId(String.valueOf(document.get("userId").toString()));
            dto.setServiceProviderId(String.valueOf(document.get("serviceProviderId").toString()));
            dto.setNumber(String.valueOf(document.get("number").toString()));
            dto.setDriverNIC(String.valueOf(document.get("driverNIC").toString()));
            dto.setName(String.valueOf(document.get("name").toString()));
            dto.setContactNumber(String.valueOf(document.get("contactNumber").toString()));
            dto.setLastPositionTime(String.valueOf(document.get("lastPositionTime")));
            dto.setAmbulanceCharge(String.valueOf(document.get("ambulanceCharge").toString()));
            HashMap<String,Object> geopoint= (HashMap<String, Object>) document.get("driverCurrentPosition");
            if (geopoint!=null){
                GeoPoint point= (GeoPoint) geopoint.get("geopoint");
                if(point!=null){
                    dto.setLatitude(point.getLatitude());
                    dto.setLongitude(point.getLongitude());
                }
            }
            dto.setFree(Boolean.parseBoolean(String.valueOf(document.get("isFree"))));
            dto.setEmail(String.valueOf(document.get("email").toString()));
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
                dto.setUserId(String.valueOf(document.get("userId").toString()));
                dto.setServiceProviderId(String.valueOf(document.get("serviceProviderId").toString()));
                dto.setNumber(String.valueOf(document.get("number").toString()));
                dto.setDriverNIC(String.valueOf(document.get("driverNIC").toString()));
                dto.setName(String.valueOf(document.get("name").toString()));
                dto.setContactNumber(String.valueOf(document.get("contactNumber").toString()));
                dto.setLastPositionTime(String.valueOf(document.get("lastPositionTime")));
                dto.setAmbulanceCharge(String.valueOf(document.get("ambulanceCharge").toString()));
                HashMap<String,Object> geopoint= (HashMap<String, Object>) document.get("driverCurrentPosition");
                if (geopoint!=null){
                    GeoPoint point= (GeoPoint) geopoint.get("geopoint");
                    if(point!=null){
                        dto.setLatitude(point.getLatitude());
                        dto.setLongitude(point.getLongitude());
                    }
                }
                dto.setFree(Boolean.parseBoolean(String.valueOf(document.get("isFree"))));
                dto.setEmail(String.valueOf(document.get("email").toString()));
                return dto;
            }
        } catch (InterruptedException  | ExecutionException e) {
            logger.info("Can not retrieve ambulance ");
        }
        return null;
    }

    public boolean editAmbulance(AmbulanceDTO dto){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(collectionName).document(dto.getUserId()).set(dto);
        try {
            UserRecord.UpdateRequest req = new UserRecord.UpdateRequest(dto.getUserId());
            req.setEmail(dto.getEmail());
            req.setPassword(dto.getPassword());
            UserRecord updateUser = FirebaseAuth.getInstance().updateUser(req);
            return true;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAmbulance(String ambulanceId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(getAmbulance(ambulanceId) != null) {
            try {
                List<String> uids = new ArrayList<>();
                uids.add(ambulanceId);
                DeleteUsersResult deleteUsersResult = FirebaseAuth.getInstance().deleteUsers(uids);
                if (deleteUsersResult.getSuccessCount()>0){
                    ApiFuture<WriteResult> writeResult = dbFirestore.collection(collectionName).document(ambulanceId).delete();
                    return true;
                }
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
            }
            return false;
        }else {
            return false;
        }
    }
}
