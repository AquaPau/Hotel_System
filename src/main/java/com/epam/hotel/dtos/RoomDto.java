package com.epam.hotel.dtos;

import com.epam.hotel.model.Room;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RoomDto {
    private long roomId;
    private int roomNumber;
    private String classId;
    private String capacity;
    private long price;

    public RoomDto (Room room){
        this.roomId = room.getRoomID();
        this.roomNumber = room.getRoomNumber();
        this.classId = room.getClassID().toString();
        this.capacity = room.getCapacity().toString();
        this.price = room.getPrice().longValue();
    }

    public Room getRoom(){
        Room room = new Room();
        room.setRoomID(this.getRoomId());
        room.setRoomNumber(this.getRoomNumber());
        room.setCapacity(Capacity.valueOf(this.getCapacity()));
        room.setClassID(ClassID.valueOf(this.getClassId()));
        room.setPrice(BigDecimal.valueOf(this.getPrice()));
        return room;
    }
}
