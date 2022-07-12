package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent {
    private String firstName;
    private String lastName;
    private ParentType parentType;
    private String CSN;

}
