package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.WordFrequency;
import com.visiblethread.docanalyzer.persistence.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.visiblethread.docanalyzer.utils.Constants.EXCLUDED_WORDS;
import static com.visiblethread.docanalyzer.utils.Constants.SAMPLE_DOCUMENT_TEXT;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MapperService mapperService;

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll()
                .stream()
                .map((doc) -> mapperService.toDocument(doc))
                .toList();
    }

    @Override
    public List<WordFrequency> getWordFrequenciesForDocument(Long documentId) {
        validateDocumentId(documentId);
        String content = getDocumentText(documentId);
        validateDocument(content);
        Map<String, Long> frequencies = calculateWordFrequencies(content);
        Map<String, Long> sortedFrequencies = sortMapByValueDescendingOrder(frequencies);
        List<WordFrequency> wordFrequencies = new ArrayList<>();
        sortedFrequencies.forEach((word, count) -> wordFrequencies.add(new WordFrequency(word, count)));
        return wordFrequencies;
    }

    @Override
    public String getDocumentText(Long documentId) {
        // Hardcoded to simulate an existing implementation as per the task description
        logger.debug("Retrieving document content for document id {}", documentId);
        return SAMPLE_DOCUMENT_TEXT;
    }

    private void validateDocumentId(Long documentId) {
        if(documentRepository.findById(documentId).isEmpty()) {
            throw new ValidationFailureException("Document id " + documentId + " does not exist");
        }
    }

    private void validateDocument(String content) {
        if(content == null || content.isEmpty()) {
            throw new ValidationFailureException("Document content cannot be empty");
        }
    }

    private Map<String, Long> calculateWordFrequencies(String content) {
        logger.debug("Calculating word frequencies");
        return Arrays.stream(content.split("[^\\w']+"))
                .filter(word -> !word.isEmpty() && !EXCLUDED_WORDS.contains(word.toLowerCase()))
                .collect(Collectors.groupingBy(word -> word , Collectors.counting()));
    }

    private Map<String, Long> sortMapByValueDescendingOrder(Map<String, Long> map) {
        logger.debug("Sorting top ten word frequencies");
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10L)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
