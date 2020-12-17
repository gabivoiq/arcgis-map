package com.isi.arcgis.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
