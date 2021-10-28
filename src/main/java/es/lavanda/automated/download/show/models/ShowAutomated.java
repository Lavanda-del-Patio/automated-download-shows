package com.lavanda.automated.download.shows.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("shows_automated")
public class ShowAutomated {

    @Id
    private String id;

    private boolean blocked;

    private String title;
}
