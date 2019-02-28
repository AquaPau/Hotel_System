package com.epam.hotel.domains;

import com.epam.hotel.domains.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.epam.hotel.utils.BookingHelper.countTotalPrice;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    public Reservation(Request request, long roomid) {
        this.id = new ReservationId(request.getId(), roomid);
        this.totalPrice = BigDecimal.valueOf(0);
        this.request = request;
    }

    public Reservation(ReservationId reservationId) {
        this.id = reservationId;
        //totalPrice = countTotalPrice();
    }
}

