package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import com.visiblethread.docanalyzer.persistence.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.Constants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private MapperService mapperService;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    public void testGetAllDocument_success() {
        TeamEntity teamEntity = createTeamEntityWithIdAndName(TEAM_1_ID, TEAM_1_NAME);
        UserEntity userEntity = createUserEntityWithEmailAndTeams(EMAIL_USER_1, Set.of(teamEntity));
        DocumentEntity documentEntity = createDocumentEntityWithAllParams(DOC_1_NAME, DOC_1_COUNT, userEntity);
        Document document = createDocument(DOC_1_NAME, DOC_1_COUNT, userEntity.getId());

        when(documentRepository.findAll()).thenReturn(List.of(documentEntity));
        when(mapperService.toDocument(documentEntity)).thenReturn(document);

        List<Document> documents = documentService.getAllDocuments();

        assertEquals(1, documents.size());
    }
}
