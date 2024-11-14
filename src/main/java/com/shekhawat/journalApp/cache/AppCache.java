package com.shekhawat.journalApp.cache;

import com.shekhawat.journalApp.entity.ConfigEntity;
import com.shekhawat.journalApp.repository.ConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public Map<String, String> APP_CACHE;

    @Autowired
    private ConfigRepository configRepository;

    @PostConstruct
    public void init() {
        APP_CACHE = new HashMap<>();
        List<ConfigEntity> configs = configRepository.findAll();
        for (ConfigEntity config: configs) {
            APP_CACHE.put(config.getKey(), config.getValue());
        }
    }

}
