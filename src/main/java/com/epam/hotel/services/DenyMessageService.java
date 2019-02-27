package com.epam.hotel.services;

import com.epam.hotel.domains.DeniedRequest;

public interface DenyMessageService {

    DeniedRequest save(DeniedRequest message);

    DeniedRequest findById(long id);
}
