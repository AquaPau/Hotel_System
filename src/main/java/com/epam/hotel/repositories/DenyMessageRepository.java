package com.epam.hotel.repositories;

import com.epam.hotel.domains.DenyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenyMessageRepository extends JpaRepository<DenyMessage, Long> {

}
