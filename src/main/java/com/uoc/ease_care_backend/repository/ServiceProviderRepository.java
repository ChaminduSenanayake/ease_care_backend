package com.uoc.ease_care_backend.repository;

import com.uoc.ease_care_backend.entity.ServiceProvider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceProviderRepository extends MongoRepository<ServiceProvider,String> {
}
