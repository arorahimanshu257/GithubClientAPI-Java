package com.octopusScope.githubClient;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    private String githubOauth;
    private String githubOrganisation;

    public AppConfig(@Value("${github.oauth}") String githubOauth, @Value("${github.login}") String githubOrganisation) {
        this.githubOauth = githubOauth;
        this.githubOrganisation = githubOrganisation;
    }

    @Bean
    public GitHub gitHub() throws IOException {
        return new GitHubBuilder().withOAuthToken(githubOauth, githubOrganisation).build();
    }
}
