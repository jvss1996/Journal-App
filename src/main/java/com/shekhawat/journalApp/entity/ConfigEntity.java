package com.shekhawat.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "config")
public class ConfigEntity {
    private String key;
    private String value;
}
