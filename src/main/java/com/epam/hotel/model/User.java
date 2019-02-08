package com.epam.hotel.model;

import com.epam.hotel.enums.Permission;
import lombok.*;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private String login;
    private char[] password;
    private Permission permission;
    private String firstName;
    private String lastName;
}
