package com.octopusScope.githubClient.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagePublisher {

    private final String exchange;
    RabbitTemplate rabbitTemplate;

    public MessagePublisher(@Value("${mq.exchange.topic}") String exchange,
                            RabbitTemplate rabbitTemplate) {
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendResponse(String url, String replyTo) {
        rabbitTemplate.convertAndSend(exchange, replyTo, url);
    }
}
