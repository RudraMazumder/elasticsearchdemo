package com.example.demo.repository;

import com.example.demo.model.Application;
import com.example.demo.model.Registration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RegistrationRepository extends ElasticsearchRepository<Registration, String> {
}
