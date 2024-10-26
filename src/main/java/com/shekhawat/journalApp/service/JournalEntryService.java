package com.shekhawat.journalApp.service;

import com.shekhawat.journalApp.entity.JournalEntry;
import com.shekhawat.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntry.setDateTime(LocalDateTime.now());
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

    public void deleteEntryById(ObjectId myId) {
        journalEntryRepository.deleteById(myId);
    }
}
