package com.epam.hotel.domains;

import com.epam.hotel.domains.enums.Capacity;
import com.epam.hotel.domains.enums.ClassID;
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

    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reservation> reservations = new ArrayList<>();

    private BigDecimal price;

    @Override
    public int compareTo(@NotNull Room room) {
        if (this.id == room.id) return 0;
        else if (this.id < room.id) return -1;
        return 1;
    }

}

