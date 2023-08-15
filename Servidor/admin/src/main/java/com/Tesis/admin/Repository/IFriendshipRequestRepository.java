package com.Tesis.admin.Repository;

import com.Tesis.admin.Models.FriendshipRequest;
import com.Tesis.admin.Models.FriendshipRequestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFriendshipRequestRepository extends JpaRepository<FriendshipRequest, FriendshipRequestId> {
}
