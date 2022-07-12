package com.example.demo.repository;

import com.example.demo.model.Notification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NotificationRepository extends ElasticsearchRepository<Notification,String> {
}
