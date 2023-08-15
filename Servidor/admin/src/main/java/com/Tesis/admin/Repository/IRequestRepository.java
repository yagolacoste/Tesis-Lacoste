package com.Tesis.admin.Repository;

import com.Tesis.admin.Models.Request;
import com.Tesis.admin.Models.RequestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRequestRepository extends JpaRepository<Request, RequestId> {
}
