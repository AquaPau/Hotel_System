package model;

import enums.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class Room {

    private int roomID;
    private int roomNumber;
    private ClassID classID;
    private Capacity capacity;
    private BigDecimal price;

}