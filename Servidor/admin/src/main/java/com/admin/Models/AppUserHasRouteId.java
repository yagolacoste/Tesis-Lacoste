package com.admin.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AppUserHasRouteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="appuser_appuser")
    private Long appUser;

    @Column(name="route_id_route")
    private Long route;

    public Long getAppUser() {
        return appUser;
    }

    public void setAppUser(Long appUser) {
        this.appUser = appUser;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }
}
