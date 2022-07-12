package com.example.demo.repository;

import com.example.demo.model.Application;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ApplicationRepository extends ElasticsearchRepository<Application, String>{

}
