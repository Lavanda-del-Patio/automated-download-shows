package com.lavanda.automated.download.shows.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavanda.automated.download.shows.exception.AutomatedDownloadShowsException;
import com.lavanda.automated.download.shows.models.LambdaDTO;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import es.lavanda.lib.common.model.MediaODTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {


    private final ShowsServiceImpl showsServiceImpl;

    @RabbitListener(queues = "agent-tmdb-feed-shows-resolution")
    public void consumeMessageTMDBFilmResolution(MediaODTO mediaODTO) throws AutomatedDownloadShowsException {
        log.debug("Reading message of the queue agent-tmdb-shows-resolution: {}", mediaODTO);
        showsServiceImpl.updateShowWithMediaDTO(mediaODTO);
        log.debug("Work message finished");
    }

    @SqsListener(value = "feed-shows-${spring.profiles.active}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consumeMessage(String lambdaDestination) throws AutomatedDownloadShowsException {
        log.debug("Reading message of the queue feed-shows: {}", lambdaDestination);
        ObjectMapper mapper = new ObjectMapper();
        LambdaDTO lambda = new LambdaDTO();
        try {
            lambda = mapper.readValue(lambdaDestination, LambdaDTO.class);
        } catch (JsonProcessingException e) {
            log.error("The message cannot convert to ShowModelTorrent", e);
            throw new AutomatedDownloadShowsException("The message cannot convert to FilmModelTorrent", e);
        }
        lambda.getShowModelTorrents().forEach(showsServiceImpl::executeShow);
        log.debug("Work message finished");
    }

}
