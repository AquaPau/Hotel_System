package com.epam.hotel.model;

import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Room implements Comparable<Room> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private int number;

    @Enumerated(EnumType.STRING)
    @Column(name = "class")
    private ClassID classID;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    private BigDecimal price;

    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL
    )
    private List<Request> requests = new ArrayList<>();

    @Override
    public int compareTo(@NotNull Room room) {
        if (this.id == room.id) return 0;
        else if (this.id < room.id) return -1;
        return 1;
    }

    public void addRequest(Request request) {
        requests.add(request);
        request.setRoom(this);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        request.setRoom(null);
    }

}

