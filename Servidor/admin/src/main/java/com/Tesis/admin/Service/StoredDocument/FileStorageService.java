package com.Tesis.admin.Service.StoredDocument;


import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Exception.FileNotFoundException;
import com.Tesis.admin.Exception.FileStorageException;
import com.Tesis.admin.Service.StoredDocument.Config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService implements IFileStorageService{
    @Autowired
    private FileStorageProperties fileStorageProperties;


    @Override
    public String storeFile(MultipartFile file, String fileName) {
        String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = originalName.substring(originalName.lastIndexOf("."));

        if (fileName==null|| fileName.isEmpty()){
            fileName = UUID.randomUUID().toString();
        }

        Path fileStorageLocation = getFileStorageLocation(getFolderName(originalName));
        Path targetLocation = fileStorageLocation.resolve(fileName + extension);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (IOException e) {
            throw new FileStorageException("No se pudo almacenar el archivo", e.getMessage());
        }
    }

    private String getFolderName(String completeFileName) {
        String extension = completeFileName.substring(completeFileName.lastIndexOf("."));
        return extension.replace(".", "").toUpperCase();
    }

    private Path getFileStorageLocation(String folderName) {
        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir() + "/" + folderName).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
            return fileStorageLocation;
        } catch (IOException e) {
            throw new FileStorageException("No se pudo crear el directorio", e.getMessage());
        }
    }

    @Override
    public Resource loadResource(String completeFileName) {
        Path fileStorageLocation = getFileStorageLocation(getFolderName(completeFileName));
        Path path = fileStorageLocation.resolve(completeFileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Archivo no encontrado: " + completeFileName, ErrorCodes.FILE_NOT_FOUND.getCode());
            }

        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Ha ocurrido un error al intentar acceder al archivo: " + completeFileName, ErrorCodes.FILE_NOT_FOUND.getCode());
        }
    }

}
