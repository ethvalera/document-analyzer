package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.WordFrequency;

import java.util.List;

public interface DocumentService {

    List<Document> getAllDocuments();

    List<WordFrequency> getWordFrequenciesForDocument(Long documentId);

    String getDocumentText(Long documentId);
}
