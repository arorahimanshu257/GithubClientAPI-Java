package com.octopusScope.githubClient;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class GithubService {

    @Autowired
    private GitHub gitHub;

    public String createRepository(String repoName, String owner, String sourceTemplate) throws IOException {

        String[] url = sourceTemplate.split("/");
        String templateOwner = url[3];
        String templateRepo = url[4];

        GHRepository repository = gitHub.createRepository(repoName)
                .private_(true)
                .fromTemplateRepository(templateOwner, templateRepo)
                .owner(owner)
                .create();
        return repository.getHtmlUrl().toString();
    }

    public String deleteRepositories(String owner, String repoName) throws IOException {
        gitHub.getOrganization(owner).getRepository(repoName).delete();
        return "deleted";
    }

    public ResponseEntity<Map> addUser(String userName, String owner) throws IOException {
        long teamId = gitHub.getOrganization(owner).getTeamByName("dod").getId();
        long[] teams = new long[]{teamId};

        String uri = "https://api.github.com/orgs/OctopusScope/invitations";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("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");
        Map<String, Object> params = new HashMap<>();
        params.put("email", userName);
        params.put("role", "admin");
        params.put("teams_id", teams);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(uri, HttpMethod.POST, entity, Map.class);
    }
}
