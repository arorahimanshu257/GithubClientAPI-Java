package com.octopusScope.githubClient.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octopusScope.githubClient.GithubService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class MessageListener {

    GithubService githubService;
    MessagePublisher publisher;

    public MessageListener(MessagePublisher publisher, GithubService service) {
        this.githubService = service;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "#{queue}")
    public void receiveMessage(Message message) throws IOException {

        Map response = new ObjectMapper().readValue(message.getBody(), Map.class);
        String replyTo = response.get("replyTo").toString();
        System.out.println("message received is: " + replyTo);


        String repoName = response.get("repoName").toString();
        String owner = response.get("owner").toString();
        String sourceTemplate = response.get("sourceTemplate").toString();

        String url = githubService.createRepository(repoName, owner, sourceTemplate);

        if (!url.isEmpty()) {
            publisher.sendResponse(url, replyTo);
        }
    }
}
