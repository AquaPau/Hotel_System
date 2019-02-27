package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.User;
import com.epam.hotel.domains.enums.BlockStatus;
import com.epam.hotel.domains.enums.Permission;
import com.epam.hotel.repositories.UserRepository;
import com.epam.hotel.services.UserService;
import com.epam.hotel.utils.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return optionalUser.get();
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setPermission(Permission.USER);
            user.setPassword(Encoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.get().getPermission() == Permission.USER){
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("You cant delete ADMIN");
        }
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Page<User> getAllUsersPaged(int page, int size) { return userRepository.findAll(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id")); }

    @Override
    public void blockById(Long id) {
        User user = userRepository.getOne(id);
        user.setBlock(BlockStatus.BLOCKED);
        userRepository.save(user);
    }

    @Override
    public void unblockById(Long id) {
        User user = userRepository.getOne(id);
        user.setBlock(BlockStatus.UNBLOCKED);
        userRepository.save(user);
    }

}

