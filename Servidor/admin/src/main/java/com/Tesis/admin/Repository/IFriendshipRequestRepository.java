package com.Tesis.admin.Repository;

import com.Tesis.admin.Dto.Request.RequestDto;
import com.Tesis.admin.Models.FriendshipRequest;
import com.Tesis.admin.Models.FriendshipRequestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFriendshipRequestRepository extends JpaRepository<FriendshipRequest, FriendshipRequestId> {

    @Query("SELECT new com.Tesis.admin.Dto.Request.RequestDto(CONCAT(fr.userDest.firstName,' ',fr.userDest.lastName),fr.userDest.id,fr.status,CONCAT(fr.userDest.storedDocument.fileName,fr.userDest.storedDocument.extension)) from FriendshipRequest fr where fr.id.userOriginId=:userOrigin and fr.status=:status")
    List<RequestDto> sentRequest(Long userOrigin,Integer status);

    @Query("SELECT new com.Tesis.admin.Dto.Request.RequestDto(CONCAT(fr.userDest.firstName,' ',fr.userDest.lastName),fr.userDest.id,fr.status,CONCAT(fr.userDest.storedDocument.fileName,fr.userDest.storedDocument.extension)) from FriendshipRequest fr where fr.id.userDestId=:userOrigin and fr.status=:status")
    List<RequestDto>  receivedRequest(Long userOrigin,Integer status);
}
