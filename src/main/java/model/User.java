package model;

import enums.Permission;
import lombok.Data;


@Data
public class User {
    int id;
    String login;
    Permission permission;
    String firstName;
    String lastName;
}
