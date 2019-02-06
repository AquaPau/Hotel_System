package model;

import enums.Permission;
import lombok.Data;


@Data
public class User {

    private long id;
    private String login;
    private Permission permission;
    private String firstName;
    private String lastName;
}
