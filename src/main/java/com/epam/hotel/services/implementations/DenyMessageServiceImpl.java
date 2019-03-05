package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.DeniedRequest;
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
    public DeniedRequest save(DeniedRequest message) {
        return denyMessageRepository.save(message);
    }

    @Override
    public DeniedRequest findById(long id) {
        Optional<DeniedRequest> messageOptional = denyMessageRepository.findById(id);
        return messageOptional.orElseThrow(() -> new RuntimeException("Deny reason not found"));
    }


}
