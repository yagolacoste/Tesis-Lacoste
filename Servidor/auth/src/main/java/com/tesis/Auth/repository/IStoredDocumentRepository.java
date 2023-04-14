package com.Tesis.auth.repository;


import com.Tesis.auth.entity.StoredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStoredDocumentRepository extends JpaRepository<StoredDocument,Long> {

    @Override
    Optional<StoredDocument> findById(Long aLong);
}