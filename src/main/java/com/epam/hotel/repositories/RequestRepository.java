package com.epam.hotel.repositories;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select r from Request r left join r.reservation s where (s.request=r and r.user = :#{#user}) order by r.id")
    Page<Request> findAllProcessedRequestsByUser(@Param("user") User user, Pageable pageable);

    @Query("select r from Request r left join r.reservation s where (s.request is null and r.user = :#{#user}) order by r.id")
    Page<Request> findAllUnprocessedRequestsByUser(@Param("user") User user, Pageable pageable);

}
