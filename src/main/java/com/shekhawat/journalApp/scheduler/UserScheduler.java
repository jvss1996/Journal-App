package com.shekhawat.journalApp.scheduler;

import com.shekhawat.journalApp.cache.AppCache;
import com.shekhawat.journalApp.entity.User;
import com.shekhawat.journalApp.enums.Sentiment;
import com.shekhawat.journalApp.model.SentimentData;
import com.shekhawat.journalApp.repository.UserRepositoryImpl;
import com.shekhawat.journalApp.service.EmailService;
import com.shekhawat.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Autowired
    private EmailService emailService;

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
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days: " + maxSentiment).build();
                try {
                    kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
                } catch (Exception ex) {
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for last 7 days", sentimentData.getSentiment());
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
