package com.visiblethread.docanalyzer.persistence.repository;

import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.TestConstants.*;
import static com.visiblethread.docanalyzer.utils.TestConstants.EMAIL_USER_1;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        teamRepository.saveAll(List.of(createTeamEntityWithName(TEAM_1_NAME)));
        TeamEntity team = teamRepository.findByNameIn(List.of(TEAM_1_NAME)).get(0);
        userRepository.save(createUserEntityWithEmailAndTeams(EMAIL_USER_1, Set.of(team)));
    }

    @Test
    public void testSaveDocumentAndFindAll() {
        Optional<UserEntity> userEntity = userRepository.findByEmail(EMAIL_USER_1);
        DocumentEntity documentEntity = createDocumentEntityWithAllParams(DOC_1_NAME, DOC_1_COUNT, userEntity.get());
        assertEquals(8, documentRepository.findAll().size());

        documentRepository.save(documentEntity);

        List<DocumentEntity> documentEntities = documentRepository.findAll();
        assertEquals(9, documentEntities.size());
    }
}
