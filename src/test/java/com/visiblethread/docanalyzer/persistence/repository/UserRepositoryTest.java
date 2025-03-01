package com.visiblethread.docanalyzer.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Commit
    public void testCountInactiveUsers() {
        Instant startDate = Instant.parse("2024-04-01T00:00:00Z");
        Instant endDate = Instant.parse("2024-05-02T00:00:00Z");
        Integer count = userRepository.countInactiveUsers(startDate, endDate);
        assertEquals(3, count);
    }
}