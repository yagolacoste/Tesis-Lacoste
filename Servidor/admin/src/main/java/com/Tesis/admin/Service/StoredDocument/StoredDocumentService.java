package com.Tesis.admin.Service.StoredDocument;


import com.Tesis.admin.Models.StoredDocument;
import com.Tesis.admin.Repository.IStoredDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class StoredDocumentService implements IStoredDocumentService {

    @Autowired
    private IStoredDocumentRepository repo;

    @Autowired
    private IFileStorageService storageService;


    @Override
    public List<StoredDocument> list() {
        return repo.list();
    }


    @Override
    public Long save(StoredDocument obj) {
        String fileName = (repo.findById(obj.getId())).orElse(new StoredDocument()).getFileName();

        String originalFilename = obj.getFile().getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        fileName = storageService.storeFile(obj.getFile(), fileName);

        obj.setFileName(fileName);
        obj.setExtension(extension);

        return repo.save(obj).getId();
    }

    public ResponseEntity<Resource> download(String completeFileName, HttpServletRequest request) {
        Resource resource = storageService.loadResource(completeFileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Override
    public ResponseEntity<Resource> downloadByFileName(String fileName, HttpServletRequest request) {
        StoredDocument doc = repo.findByFileName(fileName).orElse(new StoredDocument());
        return download(doc.getCompleteFileName(), request);
    }


//    public GenericResponse delete(Long aLong) {
//        return null;
//    }

//    public HashMap<String, Object> validate(StoredDocument obj) {
//        return null;
//    }

}
