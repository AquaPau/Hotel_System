package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.Room;
import com.epam.hotel.exceptions.RoomNumberAlreadyExistsException;
import com.epam.hotel.repositories.RoomRepository;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ReservationService reservationService;

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
        if (room.getId() != 0 || !roomRepository.findByNumber(room.getNumber()).isPresent()){
            return roomRepository.save(room);
        } else {
            throw new RoomNumberAlreadyExistsException("Room number already exists");
        }
    }

    @Override
    public Page<Room> findAllRoomsPaged(int page, int limit) {
        return roomRepository.findAll(PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Room> findAllRoomsAvailableForRequest(Request request, int page, int limit) {
        List<Room> nonEmptyRooms = reservationService.findAllApprovedReservationOfThePeriodByRequest(request).stream()
                .map(Reservation::getRoom)
                .collect(Collectors.toList());

        List<Room> collect = roomRepository.findAll().stream()
                .filter(x -> !nonEmptyRooms.contains(x))
                .filter(x -> x.getCapacity().ordinal() >= request.getCapacity().ordinal())
                .sorted(Comparator.comparing(Room::getCapacity).thenComparing(getComparator(request)))
                .collect(Collectors.toList());

        int max = (limit * (page) > collect.size()) ? collect.size() : limit * (page);
        return new PageImpl<>(collect.subList((page - 1) * limit, max), PageRequest.of(page - 1, limit), collect.size());
    }

    private Comparator<Room> getComparator(Request request) {
        return (r1, r2) -> {
            for (int i = 0; i < 3; i++) {
                if (r1.getClassID().ordinal() - i == request.getClassID().ordinal() && r2.getClassID().ordinal() - i == request.getClassID().ordinal())
                    return 0;
                if (r1.getClassID().ordinal() - i == request.getClassID().ordinal()) return -1;
                if (r2.getClassID().ordinal() - i == request.getClassID().ordinal()) return 1;

                if (r1.getClassID().ordinal() + i == request.getClassID().ordinal() && r2.getClassID().ordinal() + i == request.getClassID().ordinal())
                    return 0;
                if (r1.getClassID().ordinal() + i == request.getClassID().ordinal()) return -1;
                if (r2.getClassID().ordinal() + i == request.getClassID().ordinal()) return 1;
            }
            return Integer.compare(r2.getClassID().ordinal(), r1.getClassID().ordinal());
        };
    }

    @Override
    public int findLastNumber() {
        try {
            return roomRepository.findLastNumber();
        } catch (Exception ignored) {
            return 0;
        }
    }


}
