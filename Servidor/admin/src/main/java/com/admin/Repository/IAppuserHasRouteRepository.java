package com.admin.Repository;

import com.admin.Models.AppuserHasRoute;
import com.admin.Models.AppuserHasRouteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppuserHasRouteRepository extends JpaRepository<AppuserHasRoute,AppuserHasRouteId> {
}
