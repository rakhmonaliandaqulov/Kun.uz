package com.example.repository;

import ch.qos.logback.core.model.INamedModel;
import com.example.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {

    EmailHistoryEntity findByEmail(String email);

    List<EmailHistoryEntity> findByCreatedDate(LocalDate date);
}
