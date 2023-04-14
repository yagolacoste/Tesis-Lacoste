package com.Tesis.admin.Repository;


import com.Tesis.admin.Models.StoredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IStoredDocumentRepository extends JpaRepository<StoredDocument,Long> {

    @Query("SELECT da FROM StoredDocument da WHERE   da.deleted = false")
    List<StoredDocument> list();

    @Query("SELECT da FROM StoredDocument da WHERE da.fileName = :fileName AND da.estate = 'A' AND da.deleted = false")
    Optional<StoredDocument> findByFileName(String fileName);

}
