package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import com.epam.hotel.domains.Room;
import com.epam.hotel.repositories.ReservationRepository;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.epam.hotel.utils.BookingHelper.countTotalPrice;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RequestService requestService;
    @Autowired
    private RoomService roomService;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(ReservationId id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (!optionalReservation.isPresent()) {
            throw new RuntimeException("Reservation not found");
        }
        return optionalReservation.get();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Request request = requestService.findById(reservation.getId().getRequestId());
        Room room = roomService.findById(reservation.getId().getRoomId());
        reservation.setTotalPrice(countTotalPrice(request.getCheckIn(), request.getCheckOut(), room.getPrice()));
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteById(ReservationId id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Page<Reservation> getAllReservationsPaged(int page, int size) {
        return reservationRepository.findAllApprovedReservations(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Reservation> getAllDeniedReservationsPaged(int page, int size) {
        return reservationRepository.findAllDeniedReservations(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Reservation> findAllApprovedReservationOfThePeriodByRequest(Request request) {
        return reservationRepository.findAllApprovedReservationOfThePeriod(request.getCheckIn(), request.getCheckOut());
    }


}
