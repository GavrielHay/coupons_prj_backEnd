package com.project.GavrielsProject.controllers;
import com.project.GavrielsProject.beans.UserDetails;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.utils.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.RestController;

@RestController
abstract class ClientController {

    @Autowired
    private JWTutil jwtUtil;

    /**
     * new token in headers
     * this method extract the user details from the token received from the user, generate a new updated token with his details and wraps it in http headers.
     *
     * @param oldToken The token received from the user
     * @return a new token in a http headers
     */
    protected HttpHeaders newTokenInHeaders(String oldToken) {
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtil.extractEmail(oldToken));
        userDetails.setClientType(ClientType.valueOf(jwtUtil.extractClientType(oldToken)));
        userDetails.setUserID(Integer.parseInt(jwtUtil.extractUserID(oldToken)));
        //send ok with header of new token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtUtil.generateToken(userDetails));
        return httpHeaders;
    }


}
