package com.admin.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AppuserHasRouteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="appuser_appuser")
    private Long appuser;

    @Column(name="route_id_route")
    private Long route;

    public AppuserHasRouteId() {
    }

    public Long getAppuser() {
        return appuser;
    }

    public void setAppuser(Long appuser) {
        this.appuser = appuser;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }


}
