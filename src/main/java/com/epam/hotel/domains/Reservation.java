package com.epam.hotel.domains;

import com.epam.hotel.domains.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Reservation {

    @EmbeddedId
    ReservationId id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", updatable = false, insertable = false)
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", updatable = false, insertable = false)
    private Room room;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal totalPrice;
}

