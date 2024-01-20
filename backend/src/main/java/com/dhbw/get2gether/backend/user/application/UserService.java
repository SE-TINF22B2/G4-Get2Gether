package com.dhbw.get2gether.backend.user.application;


import com.dhbw.get2gether.backend.user.model.CreateUserCommand;
import com.dhbw.get2gether.backend.user.model.UpdateUserCommand;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.user.adapter.out.UserRepository;
import com.dhbw.get2gether.backend.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(@Autowired UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUserById(String id) {
        return userRepository.getById(id);
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }

    public User createUser(CreateUserCommand command) {
        User user = userMapper.mapToUser(command);
        return userRepository.insert(user);
    }

    public User updateUser(UpdateUserCommand command) {
        if (!userRepository.existsById(command.getId()))
            throw new IllegalArgumentException("User with ID '" + command.getId() + "' does not exist.");
        User user = userMapper.mapToUser(command);
        return userRepository.save(user);
    }
}
