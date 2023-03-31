package com.frailty.backend.controller;

import java.util.List;

import com.frailty.backend.dto.AnswerRequest;
import com.frailty.backend.entity.Answer;
import com.frailty.backend.entity.Question;
import com.frailty.backend.service.PhysicalQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/social")
@AllArgsConstructor
public class PhysicalQuestionnaireController {
    @Autowired
    private PhysicalQuestionnaireService physicalService;

    @GetMapping
    public List<Question> all() {
        return physicalService.getQuestions();
    }

    @PostMapping
    public ResponseEntity<Double> answers(Authentication authentication, @RequestBody List<AnswerRequest> answer) {
        return ResponseEntity.ok(physicalService.postAnswers(authentication.getName(), answer));
    }
    // https://stackoverflow.com/questions/49529760/springboot-get-username-from-authentication-via-controller

    @GetMapping(path="answers")
    public ResponseEntity<List<Answer>> answers(Authentication authentication, @RequestParam("patient") String email) {
        return ResponseEntity.ok(physicalService.getAnswers(authentication.getName(), email));
    }
}