package es.lavanda.automated.download.show.service;

import es.lavanda.automated.download.show.exception.AutomatedDownloadShowsException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {

    private final QueueMessagingTemplate messagingTemplate;

    private final RabbitTemplate rabbitTemplate;

    @Value("${cloud.aws.sqs.endpoint.uri.transmission}")
    private String QUEUE_TRANSMISSION;

    public void sendTorrent(Object message) throws AutomatedDownloadShowsException {
        try {
            log.info("Sending message to queue transmission {}", message);
            messagingTemplate.convertAndSend(QUEUE_TRANSMISSION, message);
            log.info("Sended message to queue transmission");
        } catch (Exception e) {
            log.error("Failed send message to queue transmission", e);
            throw new AutomatedDownloadShowsException("Failed send message to queue transmission", e);
        }
    }

    public void sendToFeedAgentTMDB(Object message) throws AutomatedDownloadShowsException {
        try {
            log.info("Sending message to queue agent-tmdb {}", message);
            rabbitTemplate.convertAndSend("agent-tmdb-feed-shows", message);
            log.info("Sended message to queue agent-tmdb");
        } catch (Exception e) {
            log.error("Failed send message to queue agent-tmdb", e);
            throw new AutomatedDownloadShowsException("Failed send message to queue agent-tmdb", e);
        }
    }
}
