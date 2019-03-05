package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.User;
import com.epam.hotel.domains.enums.BlockStatus;
import com.epam.hotel.domains.enums.Permission;
import com.epam.hotel.exceptions.PasswordDoesNotMatchException;
import com.epam.hotel.repositories.UserRepository;
import com.epam.hotel.services.UserService;
import com.epam.hotel.utils.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.epam.hotel.utils.Encoder.encode;
import static com.epam.hotel.utils.Encoder.matches;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
            user.setBlock(BlockStatus.UNBLOCKED);
            user.setPassword(encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User updatedUser, String currentPassword){
        Optional<User> user = userRepository.findByLogin(updatedUser.getLogin());
        if(!user.isPresent())
            throw new UsernameNotFoundException("UserNotFound");
        User savedUser = user.get();
        if (matches(currentPassword,savedUser.getPassword())){
            savedUser.setFirstName(updatedUser.getFirstName());
            savedUser.setLastName(updatedUser.getLastName());
            savedUser.setLogin(updatedUser.getLogin());
            if(!updatedUser.getPassword().equals("")){
                savedUser.setPassword(encode(updatedUser.getPassword()));
            }
            return userRepository.save(savedUser);
        } else {
            throw new PasswordDoesNotMatchException("Current password doesnt match");
        }
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Uesername not found"));
        if (user.getPermission() == Permission.USER){
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("You cant delete ADMIN");
        }
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Uesername not found"));
    }

    @Override
    public Page<User> getAllUsersPaged(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public void changeUserBlockForId(Long id) {
        User user = userRepository.getOne(id);
        switch (user.getBlock()) {
            case BLOCKED:
                user.setBlock(BlockStatus.UNBLOCKED);
                break;
            case UNBLOCKED:
                user.setBlock(BlockStatus.BLOCKED);
                break;
        }
        userRepository.save(user);
    }

}

