package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.User;
import com.epam.hotel.domains.enums.Status;
import com.epam.hotel.repositories.RequestRepository;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
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
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private ReservationService reservationService;

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request findById(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (!optionalRequest.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return optionalRequest.get();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Page<Request> getPagedUnprocessedRequestByUser(User user, int page, int size) {
        return requestRepository.findAllUnprocessedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Request> getPagedProcessedRequestByUser(User user, int page, int size) {
        return requestRepository.findAllProcessedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    /*@Override
    public List<Request> getAllProceccedRequestList() {
        return reservationService.findAll().stream()
                .map(Reservation::getRequest)
                .collect(Collectors.toList());
    }*/

    /*public List<Request> filterRequestListByRequest(Request request, List<Request> requests) {
        return requests.stream()
                .filter(x -> !isInPeriod(request, x))
                .filter(x -> x.getReservation().getStatus() != Status.BILLSENT)
                .filter(x -> x.getReservation().getStatus() != Status.PAID)
                //add filters
                .collect(Collectors.toList());
    }*/

    /*private boolean isInPeriod(Request request1, Request request2) {
        Date r1ci = request1.getCheckIn();
        Date r1co = request1.getCheckOut();
        Date r2ci = request2.getCheckIn();
        Date r2co = request2.getCheckOut();

        boolean result1 = r1ci.after(r2ci) && r1ci.before(r2co);
        boolean result2 = r1co.after(r2ci) && r1co.before(r2co);
        return result1 || result2;
    }
*/

}
