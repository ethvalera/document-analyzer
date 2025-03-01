package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.api.DocumentsApi;
import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.WordFrequency;
import com.visiblethread.docanalyzer.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class DocumentController implements DocumentsApi {

    @Autowired
    private DocumentService documentService;

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Override
    public ResponseEntity<List<Document>> getAllDocuments() {
        logger.debug("Received request to get all documents");
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<WordFrequency>> getWordFrequencies(@PathVariable Long documentId) {
        logger.debug("Received request to get top ten word frequencies for document id {}", documentId);
        return new ResponseEntity<>(documentService.getWordFrequenciesForDocument(documentId), HttpStatus.OK);
    }
}
