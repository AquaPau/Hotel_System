package com.epam.hotel.repositories;

import com.epam.hotel.domains.DeniedRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenyMessageRepository extends JpaRepository<DeniedRequest, Long> {

}
