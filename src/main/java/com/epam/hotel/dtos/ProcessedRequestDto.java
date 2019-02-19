package com.epam.hotel.dtos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.Room;
import com.epam.hotel.utils.DateFormatter;

public class ProcessedRequestDto extends RequestDto{

    private long roomId;
    private long price;

    public ProcessedRequestDto(Request request, Room room) {
        super(request);
        this.roomId = room.getRoomID();
        this.price = DateFormatter.getDifferenceInDays(request.getCheckOut(), request.getCheckIn()) * room.getPrice().longValue();
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
