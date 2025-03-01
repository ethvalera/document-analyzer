package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.EntityNotFoundException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.WordFrequency;
import com.visiblethread.docanalyzer.persistence.entity.DocumentEntity;
import com.visiblethread.docanalyzer.persistence.entity.TeamEntity;
import com.visiblethread.docanalyzer.persistence.entity.UserEntity;
import com.visiblethread.docanalyzer.persistence.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.visiblethread.docanalyzer.utils.TestConstants.*;
import static com.visiblethread.docanalyzer.utils.TestDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "document.text=The contract offer includes terms and conditions. I signed the contract with a pen and sent a letter to him."
})
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

    @Test
    void testGetMostFrequentWords_documentIdDoesNotExist_badRequest() {
        Long documentId = 1L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> documentService.getWordFrequenciesForDocument(documentId));

        assertEquals("Field 'Document ID' with value '1' not found", exception.getMessage());
    }

    @Test
    void testGetMostFrequentWords_emptyContent_validationFailure() {
        ReflectionTestUtils.setField(documentService, "documentText", "");
        Long documentId = 1L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(new DocumentEntity()));

        ValidationFailureException exception = assertThrows(ValidationFailureException.class,
                () -> documentService.getWordFrequenciesForDocument(documentId));

        assertEquals("Document content cannot be empty", exception.getMessage());
    }

    @Test
    void testGetMostFrequentWords_nullContent_validationFailure() {
        ReflectionTestUtils.setField(documentService, "documentText", null);
        Long documentId = 1L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(new DocumentEntity()));

        ValidationFailureException exception = assertThrows(ValidationFailureException.class,
                () -> documentService.getWordFrequenciesForDocument(documentId));

        assertEquals("Document content cannot be empty", exception.getMessage());
    }

    @Test
    void testGetMostFrequentWords_lessThanTenWords_success() {
        ReflectionTestUtils.setField(documentService, "documentText", "Only three only");
        Long documentId = 1L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(new DocumentEntity()));

        List<WordFrequency> wordFrequencies = documentService.getWordFrequenciesForDocument(documentId);

        assertEquals(2, wordFrequencies.size());
        assertEquals("only", wordFrequencies.get(0).getWord());
        assertEquals(2L, wordFrequencies.get(0).getCount());
    }

    @Test
    void testGetMostFrequentWords_success() {
        ReflectionTestUtils.setField(documentService, "documentText", "The contract offer includes terms and conditions. " +
                "   I signed the contract with a pen and sent a letter to him.");
        Long documentId = 1L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(new DocumentEntity()));

        List<WordFrequency> wordFrequencies = documentService.getWordFrequenciesForDocument(documentId);

        assertEquals(10, wordFrequencies.size());
        assertEquals("contract", wordFrequencies.get(0).getWord());
        assertEquals(2L, wordFrequencies.get(0).getCount());
    }

}
