package com.epam.hotel.domains;

import com.epam.hotel.domains.enums.Capacity;
import com.epam.hotel.domains.enums.ClassID;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Room{

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

}

