package com.kirilarsov.mtls.client.controller;

import com.kirilarsov.mtls.client.service.server.ServerWebService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    private final ServerWebService serverWebService;

    public ServerController(ServerWebService serverWebService) {
        this.serverWebService = serverWebService;
    }

    @GetMapping("/health-server")
    public ResponseEntity getServerHealth() {
        return new ResponseEntity<>(serverWebService.health(), HttpStatus.OK);
    }
}
