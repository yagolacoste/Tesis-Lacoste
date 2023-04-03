package com.Tesis.auth.repository;


import com.Tesis.auth.entity.StoredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStoredDocumentRepository extends JpaRepository<StoredDocument,Long> {
}
