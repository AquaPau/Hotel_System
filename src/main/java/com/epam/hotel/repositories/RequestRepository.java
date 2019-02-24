package com.epam.hotel.repositories;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByUserAndRoomNull(User user, Pageable pageable);

    Page<Request> findAllByUserAndRoomNotNull(User user, Pageable pageable);


}
