package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.DenyMessage;
import com.epam.hotel.repositories.DenyMessageRepository;
import com.epam.hotel.services.DenyMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DenyMessageServiceImpl implements DenyMessageService {

    private final DenyMessageRepository denyMessageRepository;

    @Override
    public DenyMessage save(DenyMessage message) {
        return denyMessageRepository.save(message);
    }

    @Override
    public DenyMessage findById(long id) {
        Optional<DenyMessage> messageOptional = denyMessageRepository.findById(id);
        if (!messageOptional.isPresent()) {
            throw new RuntimeException("Deny message not found");
        }
        return messageOptional.get();
    }


}
