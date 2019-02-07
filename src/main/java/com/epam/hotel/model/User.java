package com.epam.hotel.model;

import com.epam.hotel.enums.Permission;
import lombok.*;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    @NonNull
    private String login;
    @NonNull
    private char[] password;
    private Permission permission;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
