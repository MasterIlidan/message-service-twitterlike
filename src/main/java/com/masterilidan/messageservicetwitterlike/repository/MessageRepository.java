package com.masterilidan.messageservicetwitterlike.repository;

import com.masterilidan.messageservicetwitterlike.entity.Message;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByCreatedBy(Long id);

    List<Message> findAllByCreatedBy(@NotNull long createdBy, Pageable pageable);
}
