package com.epam.hotel.domains;

import com.epam.hotel.domains.enums.Capacity;
import com.epam.hotel.domains.enums.ClassID;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "class")
    private ClassID classID;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkIn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOut;

    @OneToOne(
            mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Reservation reservation;

}
