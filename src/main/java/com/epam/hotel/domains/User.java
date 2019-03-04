package com.epam.hotel.domains;

import com.epam.hotel.domains.enums.BlockStatus;
import com.epam.hotel.domains.enums.Permission;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private BlockStatus block;

    public void addRequest(Request request) {
        requests.add(request);
        request.setUser(this);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        request.setUser(null);
    }

    public boolean isNotBlocked(){
        switch (block) {
            default:
            case BLOCKED:
                return false;
            case UNBLOCKED:
                return true;
        }
    }
}
