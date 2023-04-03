package com.Tesis.admin.Service.StoredDocument;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;



public interface IFileStorageService {

    public String storeFile(MultipartFile file, String fileName);

    public Resource loadResource(String completeFileName);
}
