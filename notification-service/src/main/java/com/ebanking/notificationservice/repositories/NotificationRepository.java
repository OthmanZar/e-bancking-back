package com.ebanking.notificationservice.repositories;


import com.ebanking.notificationservice.entities.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {

}
