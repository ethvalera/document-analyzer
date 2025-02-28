package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

}
