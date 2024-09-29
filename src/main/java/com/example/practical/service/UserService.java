package com.example.practical.service;

import com.example.practical.model.User;
import com.example.practical.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final String auditTopic = "audit-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        kafkaTemplate.send(auditTopic, "Created User: " + savedUser.getId());
        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        kafkaTemplate.send(auditTopic, "Updated User: " + updatedUser.getId());
        return updatedUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        kafkaTemplate.send(auditTopic, "Deleted User: " + id);
    }
}
