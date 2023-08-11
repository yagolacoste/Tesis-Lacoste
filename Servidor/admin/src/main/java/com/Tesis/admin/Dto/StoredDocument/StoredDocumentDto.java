package com.Tesis.admin.Dto.StoredDocument;


import com.Tesis.admin.Models.StoredDocument;

import java.io.Serializable;


public class StoredDocumentDto implements Serializable {
  private long id;

  private String name;


  private String fileName;

  private String extension;




  public StoredDocumentDto(StoredDocument s) {

    this.id = s.getId();
    this.name = s.getName();
    this.fileName = s.getFileName();
    this.extension = s.getExtension();
  }



  public StoredDocumentDto() {

  }



  public long getId() {

    return id;
  }



  public void setId(long id) {

    this.id = id;
  }



  public String getName() {

    return name;
  }



  public void setName(String name) {

    this.name = name;
  }



  public String getFileName() {

    return fileName;
  }



  public void setFileName(String fileName) {

    this.fileName = fileName;
  }



  public String getExtension() {

    return extension;
  }



  public void setExtension(String extension) {

    this.extension = extension;
  }
}
