package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.persistence.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

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
}
