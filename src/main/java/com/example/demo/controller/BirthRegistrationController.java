package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.RegistrationRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RestController
public class BirthRegistrationController {

    private static Logger LOGGER= LoggerFactory.getLogger(BirthRegistrationController.class);

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private RegistrationRepository registrationRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;



    @PostMapping("/notifications")
    public String saveNotification(@RequestBody Notification notification) {

        String fullName = notification.getNotifierFirstName()+" "+notification.getNotifierLastName();
        LOGGER.info("Notification for {} recieved", fullName);

        double latitude = notification.getLatitude();
        double longitude = notification.getLongitude();

        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        notification.setGeoPoint(geoPoint);

        notificationRepo.save(notification);
        LOGGER.info("Notification for {} saved with number {}", fullName, notification.getDocumentNumber());
        return notification.getId();


    }

    @DeleteMapping("/notifications/{id}")
    private String deleteNotification(@PathVariable String id){
        notificationRepo.deleteById(id);
        LOGGER.info("Notification {} deleted", id);
        return id;
    }

    @DeleteMapping("/applications/{id}")
    private String deleteApplications(@PathVariable String id){
        applicationRepo.deleteById(id);
        LOGGER.info("Application {} deleted", id);
        return id;
    }

    @DeleteMapping("/registrations/{id}")
    private String deleteRegistrations(@PathVariable String id){
        registrationRepo.deleteById(id);
        LOGGER.info("Registration {} deleted", id);
        return id;
    }

    @PostMapping("/applications")
    private String saveApplication(@RequestBody Application application) {
        String fullName = application.getFirstName() + " "+application.getLastName();
        LOGGER.info("Application for {} recieved", fullName);
        applicationRepo.save(application);
        LOGGER.info("Application for {} saved with number {}", fullName, application.getDocumentNumber());
        return application.getId();
    }

    @PostMapping("/registrations")
    private String saveRegistrations(@RequestBody Registration registration) {
        String fullName = registration.getFirstName() + " "+registration.getLastName();
        LOGGER.info("Registration for {} recieved", fullName);
        registrationRepo.save(registration);
        LOGGER.info("Registration for {} saved with number {}", fullName, registration.getDocumentNumber());
        return registration.getId();
    }

    @GetMapping("/notifications/{searchTerm}")
    public List<Notification> searchNotifications(@PathVariable String searchTerm){
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(searchTerm))
                .build();

        SearchHits<Notification> search = elasticsearchOperations.search(nativeSearchQuery, Notification.class, IndexCoordinates.of("birth*"));
        List<Notification> notificationList = new ArrayList<>();
        boolean hasSearchHits = search.hasSearchHits();

        if(hasSearchHits){
            LOGGER.info("Found hit with count {} for the search term {}", search.getTotalHits(), searchTerm);
            Iterator<SearchHit<Notification>> iterator = search.iterator();
            while(iterator.hasNext()){
                SearchHit<Notification> next = iterator.next();
                Notification content = next.getContent();
                LOGGER.info("Document Id {}, First Name {}", content.getId(), content.getFirstName());
                notificationList.add(content);

            }


        } else {
            LOGGER.error("No Search hits for the term {}", searchTerm);
            return null;
        }
        return notificationList;
    }

}
