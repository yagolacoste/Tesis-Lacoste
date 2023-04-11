package com.Tesis.admin.Service.StoredDocument;


import com.Tesis.admin.Models.StoredDocument;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IStoredDocumentService {

    List<StoredDocument> list();

    Long save(StoredDocument obj);

     ResponseEntity<Resource> downloadByFileName(String fileName, HttpServletRequest request);

     ResponseEntity<Resource> download(String completeFileName, HttpServletRequest request);
}
