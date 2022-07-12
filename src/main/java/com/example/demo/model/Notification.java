package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "birth_notification_2")
public class Notification extends BaseDocument {

    @Id
    private String id;
    private String notifierFirstName;
    private String notifierLastName;

    @Field(type=FieldType.Date, format={}, pattern="uuuu-MM-dd")
    private LocalDate dob;

    @Field(type=FieldType.Date, format={}, pattern="uuuu-MM-dd")
    private LocalDate notificationDate;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Parent> parentList;

    private double latitude;
    private double longitude;

    @GeoPointField
    private GeoPoint geoPoint;



}
