package com.shekhawat.journalApp.service;

import com.shekhawat.journalApp.entity.JournalEntry;
import com.shekhawat.journalApp.entity.User;
import com.shekhawat.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            log.error("Exception: ", ex);
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteUserById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
