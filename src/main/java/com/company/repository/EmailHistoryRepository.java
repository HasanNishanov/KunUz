package com.company.repository;


import com.company.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {
}
