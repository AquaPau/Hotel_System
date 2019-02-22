package com.epam.hotel.model;

import com.epam.hotel.model.enums.*;
import lombok.*;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Persistent
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor

public class Room implements Comparable<Room> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomID;
    @Column(name = "roomnumber")
    private int roomNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "classid")
    private ClassID classID;
    @Enumerated(EnumType.STRING)
    @Column(name = "capacity")
    private Capacity capacity;
    @Column(name = "price")
    private BigDecimal price;


    @Override
    public int compareTo(@NotNull Room room) {
        if (this.roomID == room.getRoomID()) return 0;
        else if (this.roomID < room.getRoomID()) return -1;
        return 1;
    }


}

