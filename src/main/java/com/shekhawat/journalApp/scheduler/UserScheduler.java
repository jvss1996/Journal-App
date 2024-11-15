package com.shekhawat.journalApp.scheduler;

import com.shekhawat.journalApp.cache.AppCache;
import com.shekhawat.journalApp.entity.User;
import com.shekhawat.journalApp.enums.Sentiment;
import com.shekhawat.journalApp.repository.UserRepositoryImpl;
import com.shekhawat.journalApp.service.EmailService;
import com.shekhawat.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendEmails() {
        List<User> users = userRepository.getUserForSA();
        for(User user: users) {
            List<Sentiment> filteredEntries = user.getJournalEntries().stream().filter(x -> x.getDateTime().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).toList();
            Map<Sentiment, Integer> sentiments = new HashMap<>();
            for (Sentiment sentiment: filteredEntries) {
                if (sentiment != null) {
                    sentiments.put(sentiment, sentiments.getOrDefault(sentiment, 0) + 1);
                }
            }
            int maxCount = 0;
            Sentiment maxSentiment = null;
            for(Map.Entry<Sentiment, Integer> e: sentiments.entrySet()) {
                if (e.getValue() > maxCount) {
                    maxCount = e.getValue();
                    maxSentiment = e.getKey();
                }
            }
            if (maxSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", maxSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
