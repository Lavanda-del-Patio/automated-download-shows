package com.lavanda.automated.download.shows.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TelegramMessageRequest {

    private String to;

    private String textMessage;

    private String photo;

}
