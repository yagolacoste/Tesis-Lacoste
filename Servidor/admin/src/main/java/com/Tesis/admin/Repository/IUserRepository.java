package com.Tesis.admin.Repository;

import com.Tesis.admin.Dto.Request.RequestDto;
import com.Tesis.admin.Dto.Request.UserSearch;
import com.Tesis.admin.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.Collection;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

  User findByEmail(String email);

  Boolean existsByEmail(String email);


  @Query(value = "SELECT CONCAT(ap.first_name, ' ', ap.last_name) AS nameComplete, ap.id AS userDest, " +
          "CASE WHEN ap.id NOT IN (SELECT f.friends_id FROM friends f WHERE f.user_id = ?1) " +
          "AND fr.status IS NULL THEN 3 ELSE COALESCE(fr.status, 3) END AS status, " +
          "CONCAT(sd.file_name, '', sd.extension) AS fileName " +
          "FROM appuser ap " +
          "LEFT JOIN friendshiprequest fr ON ap.id = fr.user_dest AND fr.user_origin = ?1 AND fr.status = ?2 " +
          "INNER JOIN stored_document sd ON ap.stored_document_id = sd.id " +
          "WHERE (ap.id NOT IN (SELECT f.friends_id FROM friends f WHERE f.user_id = ?1)) " +
          "AND ap.id <> ?1", nativeQuery = true)
  List<UserSearch> getAllNotFriendsAndPending(Long user, Integer status);


}
