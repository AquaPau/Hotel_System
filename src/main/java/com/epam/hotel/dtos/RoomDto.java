package com.epam.hotel.dtos;

import com.epam.hotel.model.Room;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.utils.DateFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RoomDto implements Comparable<RoomDto> {
    private long roomID;
    private int roomNumber;
    private String classID;
    private String capacity;
    private String price;

    public RoomDto(Room room) {
        this.roomID = room.getRoomID();
        this.roomNumber = room.getRoomNumber();
        this.capacity = room.getCapacity().name();
        this.classID = room.getClassID().name();
        this.price = room.getPrice().toString();
    }

    public Room getRoom() {
        Room room = new Room();
        room.setRoomID(this.getRoomID());
        room.setRoomNumber(this.getRoomNumber());
        room.setCapacity(Capacity.valueOf(this.capacity));
        room.setClassID(ClassID.valueOf(this.classID));
        room.setPrice(new BigDecimal(this.price));
        return room;
    }

    @Override
    public int compareTo(@NotNull RoomDto roomDto) {
        if (this.roomID == roomDto.getRoomID()) return 0;
        else if (this.roomID < roomDto.getRoomID()) return -1;
        return 1;
    }

}