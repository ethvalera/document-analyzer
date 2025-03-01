package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("""
    SELECT COUNT(u) FROM UserEntity u 
    WHERE u.createdAt < :endDate
    AND NOT EXISTS (
        SELECT d FROM DocumentEntity d 
        WHERE d.userEntity = u
        AND d.uploadedAt BETWEEN :startDate AND :endDate
    )
   \s""")
    Integer countInactiveUsers(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

}