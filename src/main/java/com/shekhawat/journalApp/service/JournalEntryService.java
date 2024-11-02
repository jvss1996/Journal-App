package com.shekhawat.journalApp.service;

import com.shekhawat.journalApp.entity.JournalEntry;
import com.shekhawat.journalApp.entity.User;
import com.shekhawat.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUsername(username);
            journalEntry.setDateTime(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        } catch (Exception ex) {
            log.error("Exception: ", ex);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception ex) {
            log.error("Exception: ", ex);
        }
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId myId, String username) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(myId);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Exception: ", ex);
        }
        return removed;
    }
}
