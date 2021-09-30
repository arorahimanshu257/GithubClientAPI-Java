package com.octopusScope.githubClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GithubController {

    private final GithubService service;

    public GithubController(GithubService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public String createRepository(@RequestBody Map<String, String> data) throws IOException {

        return service.createRepository(data.get("name"),
                data.get("owner"),
                data.get("sourceTemplate"));
    }

    @PostMapping("/user")
    public ResponseEntity<Map> addUser(@RequestBody Map<String, String> data) throws IOException, InterruptedException {
        System.out.println(data.get("userName"));
        return service.addUser(data.get("userName"),
                data.get("owner"));
    }

    @DeleteMapping("/delete")
    public String deleteRepositories(@RequestBody Map<String, String> data) throws IOException {
        return service.deleteRepositories(data.get("owner"), data.get("repoName"));
    }
}
