package com.frailty.backend.controller;

import com.frailty.backend.entity.Result;
import com.frailty.backend.service.ResultsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/results")
@AllArgsConstructor
public class ResultsController {
    @Autowired
    private ResultsService resultsService;

    @GetMapping
    public ResponseEntity<List<Result>> all(Authentication authentication, @RequestParam("patient") String email) {
        return ResponseEntity.ok(resultsService.getResults(authentication.getName(), email)); }
}
