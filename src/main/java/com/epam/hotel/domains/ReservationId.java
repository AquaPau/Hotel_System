package com.epam.hotel.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationId implements Serializable {


    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "room_id")
    private Long roomId;
}
