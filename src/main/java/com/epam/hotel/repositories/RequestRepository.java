package com.epam.hotel.repositories;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Long> {

    // processed requests by user
    @Query("select r from Request r left join r.reservation s where (s.request=r and r.user = :#{#user}) order by r.id")
    Page<Request> findProcessedRequestsByUser(User user, Pageable pageable);

    // unprocessed requests by user
    @Query("select r from Request r left join r.reservation s where (s.request is null and r.user = :#{#user}) order by r.id")
    Page<Request> findUnprocessedRequestsByUser(User user, Pageable pageable);

    // denied requests by user
    Page<Request> findAllByUserAndDeniedRequestReasonIsNotNull(User user, Pageable pageable);


    // all unprocessed requests
    Page<Request> findAllByReservationIsNullAndDeniedRequestReasonIsNull(Pageable pageable);

    // all denied requests
    Page<Request> findAllByDeniedRequestReasonIsNotNull(PageRequest id);

    // all processed requests
    Page<Request> findAllByReservationIsNotNull(Pageable pageable);

}
