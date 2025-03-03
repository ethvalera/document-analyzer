package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.exception.DocAnalyzerException;
import com.visiblethread.docanalyzer.exception.EntityNotFoundException;
import com.visiblethread.docanalyzer.exception.ValidationFailureException;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.LongestWordSynonyms;
import com.visiblethread.docanalyzer.model.WordFrequency;
import com.visiblethread.docanalyzer.persistence.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.visiblethread.docanalyzer.utils.Constants.EXCLUDED_WORDS;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Value("${document.text}")
    private String documentText;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private GeminiAIService geminiAIService;

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
        return documentText;
    }

    @Override
    public LongestWordSynonyms getLongestWordSynonyms(Long documentId) {
        validateDocumentId(documentId);
        String content = getDocumentText(documentId);
        String longestWord = getLongestWord(content);
        return new LongestWordSynonyms(longestWord, getSynonyms(longestWord));
    }

    private void validateDocumentId(Long documentId) {
        if(documentRepository.findById(documentId).isEmpty()) {
            throw new EntityNotFoundException("Document ID", documentId.toString());
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
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }

    private Map<String, Long> sortMapByValueDescendingOrder(Map<String, Long> map) {
        logger.debug("Sorting top ten word frequencies");
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10L)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private String getLongestWord(String content) {
        logger.debug("Obtaining the longest");
        return Arrays.stream(content.split("[^\\w']+"))
                .filter(word -> !word.isEmpty() && !EXCLUDED_WORDS.contains(word.toLowerCase()))
                .reduce("", (longest, curr) -> (longest.length() >= curr.length() ? longest : curr));
    }

    private List<String> getSynonyms(String word) {
        logger.debug("Request to Gemini to provide synonyms for {}", word);
        String prompt = "Please provide 5 synonyms for the word '" + word + "'. " +
                "Return them as a simple comma-separated list without additional text.";
        String response = geminiAIService.generateTextResponse(prompt);
        if(response == null) {
            throw new DocAnalyzerException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting Gemini response");
        }
        return Arrays.stream(response.split(",\\s*"))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
