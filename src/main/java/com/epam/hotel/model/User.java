package com.epam.hotel.model;

import com.epam.hotel.model.enums.Permission;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;
    private String password;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Request> requests = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Permission permission;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    public void addRequest(Request request) {
        requests.add(request);
        request.setUser(this);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        request.setUser(null);
    }

}
