package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.Room;
import com.epam.hotel.domains.enums.Capacity;
import com.epam.hotel.domains.enums.Status;
import com.epam.hotel.repositories.RoomRepository;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private RequestService requestService;
    private ReservationService reservationService;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
            throw new RuntimeException("Room not found!");
        }
        return optionalRoom.get();
    }

    @Override
    public void deleteById(long id) {
        roomRepository.deleteById(id);

    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Page<Room> findAllRoomsPaged(int page, int limit) {
        return roomRepository.findAll(PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id"));
    }


    /*@Override
    public Page<Room> findPagedAllFittingRooms(Request request, int page, int limit) {
        List<Room> rooms = findAllRoomsAvailableForRequest(request);
        PageRequest
    }

    public List<Room> findAllRoomsAvailableForRequest(Request request) {
        List<Request> filteredRequests = requestService.filterRequestListByRequest(request, requestService.getAllProceccedRequestList());
        List<Room> collect = filteredRequests.stream()
                .map(x -> x.getReservation().getRoom())
                .collect(Collectors.toList());
        collect.addAll(findAllAvailableRooms());
        return collect;
    }



    @Override
    public List<Room> findAllAvailableRooms() {
        return findAll().stream()
                .filter(x -> x.getReservations().isEmpty())
                .collect(Collectors.toList());
    }
*/
}
