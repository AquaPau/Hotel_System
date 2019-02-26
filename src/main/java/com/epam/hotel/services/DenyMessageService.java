package com.epam.hotel.services;

import com.epam.hotel.domains.DenyMessage;

public interface DenyMessageService {

    DenyMessage save(DenyMessage message);

    DenyMessage findById(long id);
}
