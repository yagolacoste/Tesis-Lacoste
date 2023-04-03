package com.Tesis.admin.Repository;

import com.Tesis.admin.Models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRouteRepository extends JpaRepository<Route,String> {


}
