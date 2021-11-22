package com.project.GavrielsProject.beans;

import com.project.GavrielsProject.enums.ClientType;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserDetails {
    /**
     * user details of online user to perform login check or insert into token.
     */
    private ClientType clientType;
    private String email;
    private String password;
    private int userID;
}
