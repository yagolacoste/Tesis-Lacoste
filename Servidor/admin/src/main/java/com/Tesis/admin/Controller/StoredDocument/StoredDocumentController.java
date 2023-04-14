package com.Tesis.admin.Controller.StoredDocument;


import com.Tesis.admin.Models.StoredDocument;
import com.Tesis.admin.Service.StoredDocument.IStoredDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class StoredDocumentController implements IStoredDocumentController {
    @Autowired
    private IStoredDocumentService service;

    @Override
    public List<StoredDocument> list() {
        return service.list();
    }

    @Override
    public ResponseEntity<Resource> download(String fileName, HttpServletRequest request) {
        return service.download(fileName,request);
    }

    @Override
    public Long save(StoredDocument obj) {

        return service.save(obj);
    }


}
