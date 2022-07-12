package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "birth_application")
public class Application extends BaseDocument{

    @Id
    private String id;

    @Field(type=FieldType.Date, format={}, pattern="uuuu-MM-dd")
    private Date applicationDate;


    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Parent> parentList;



}
