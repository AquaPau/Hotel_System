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

    // unprocessed requests by user
    @Query("select r from Request r left join r.reservation s where (s.request is null and r.user = :#{#user} and r.deniedRequest.reason is null) order by r.id desc")
    Page<Request> findUnprocessedRequestsByUser(@Param("user") User user, Pageable pageable);

    // processed requests by user
    @Query("select r from Request r left join r.reservation s where (s.request=r and r.user = :#{#user}) order by r.id desc")
    Page<Request> getProcessedRequestsByUser(@Param("user") User user, Pageable pageable);

    // denied requests by user
    @Query("select r from Request r left join r.deniedRequest s where (s.request=r and s.reason is not null and r.user = :#{#user}) order by r.id desc")
    Page<Request> findDeniedRequestsByUser(@Param("user") User user, Pageable pageable);


    // all processed requests
    @Query("select r from Request r left join r.reservation s where (s.request=r) order by r.id")
    Page<Request> findAllProcessedRequests(Pageable pageable);

    // all unprocessed requests
    @Query("select r from Request r left join r.reservation s where (s.request is null and r.deniedRequest.reason is null) order by r.id")
    Page<Request> findAllUnprocessedRequests(Pageable pageable);

    // all denied requests
    @Query("select r from Request r left join r.deniedRequest s where (s.request=r and s.reason is not null) order by r.id")
    Page<Request> findAllDeniedRequests(PageRequest id);


    @Query("select count (r) from Request r left join r.reservation s where (s.request is null and r.user = :#{#user} and r.deniedRequest.reason is null)")
    long countUnprocessedRequestByUser(@Param("user") User user);

    @Query("select count (r) from Request r left join r.reservation s where (s.request=r and r.user = :#{#user})")
    long countProcessedRequestByUser(@Param("user") User user);

    @Query("select count (r) from Request r left join r.deniedRequest s where (s.request=r and s.reason is not null and r.user = :#{#user})")
    long countDeniedRequestByUser(@Param("user") User user);

    @Query("select count (r) from Request r left join r.reservation s where (s.request is null and r.deniedRequest.reason is null)")
    long countAllPendingRequestForAdmin();

    @Query("select count (r) from Request r left join r.reservation s where (s.request=r)")
    long countAllApprovedRequestForAdmin();

    @Query("select count (r) from Request r left join r.deniedRequest s where (s.request=r and s.reason is not null)")
    long countAllDeniedRequestForAdmin();



}
