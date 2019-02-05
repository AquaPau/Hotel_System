package model;

import enums.Permission;
import lombok.Data;


@Data
public class User {
    long id;
    String login;
    Permission permission;
    String firstName;
    String lastName;
}
