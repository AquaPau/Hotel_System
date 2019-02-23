package com.epam.hotel.model;

import com.epam.hotel.model.enums.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    private String firstname;
    private String lastname;

}
