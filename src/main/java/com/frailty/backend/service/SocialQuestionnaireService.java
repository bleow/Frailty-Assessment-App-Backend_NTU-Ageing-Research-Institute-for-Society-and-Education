package com.frailty.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.frailty.backend.dto.AnswerRequest;
import com.frailty.backend.entity.*;
import com.frailty.backend.entity.scoring.ScoringStrategy;
import com.frailty.backend.output.Localiser;
import com.frailty.backend.repository.AnswerRepository;
import com.frailty.backend.repository.QuestionRepository;
import com.frailty.backend.repository.ResultRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SocialQuestionnaireService {
    private Localiser localiser;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private ResultRepository resultRepository;
    private AppUserService appUserService;

    public List<Question> getQuestions() {
        List<Question> socialQuestions = questionRepository.findByQuestionType(QuestionType.SOCIAL);
        return socialQuestions;
    }

    public Double postAnswers(String username, List<AnswerRequest> answerRequest) {
        log.warn("==TESTING== USERNAME {}", username);
        AppUser appUser = appUserService.getValidUser(username);
        ArrayList<Answer> answers = new ArrayList<>();
        Double totalScore = 0.0;
        Double maxScore = 0.0;
        for (AnswerRequest request : answerRequest) {
            Question question = questionRepository.findByQuestionNumberAndQuestionType(request.questionNumber(), QuestionType.SOCIAL).orElseThrow(() -> new IllegalStateException(localiser.notFound("Question ID", request.questionNumber().toString())));
            ScoringStrategy strategy = question.getStrategy();
            log.warn("==TESTING== QUESTION {} {}", question.getQuestionText(), strategy.getCorrectAnswers().toString());
            Double answerScore = strategy.calculateScore(request.answerText());
            maxScore += strategy.getMaxScore();
            totalScore += answerScore;
            Answer answer = new Answer(appUser, request.datetime(), question, request.answerTextToString(), answerScore);
            answers.add(answer);
        }
        answerRepository.saveAll(answers);

        Double MAX_SCORE = questionRepository.findByQuestionType(QuestionType.SOCIAL).stream().map(Question::getStrategy).map(ScoringStrategy::getMaxScore).reduce(0.0, Double::sum);
        if (Double.compare(maxScore, MAX_SCORE) > 0) {
            throw new IllegalStateException(localiser.fail(String.format("Score cannot be greater than %f. Current score is %f", MAX_SCORE, maxScore)));
        } else if (Double.compare(maxScore, MAX_SCORE) == 0) {
            LocalDateTime resultsDateTime = answers.stream().map(Answer::getDatetime).min(LocalDateTime::compareTo).get();
            String overallBanding;
            if (totalScore <= 1) {
                overallBanding = "Non-frail";
            } else if (totalScore <= 3) {
                overallBanding = "Pre-frail";
            } else {
                overallBanding = "Frail";
            }
            resultRepository.save(new Result(resultsDateTime, QuestionType.SOCIAL, totalScore, overallBanding, appUser));
        }

        return totalScore;
    }

    public List<Answer> getAnswers(String username, String email) {
        AppUser clinician = appUserService.getValidUser(username);
        AppUser patient = appUserService.getValidUser(email);
        return answerRepository.findByAppUser(patient);
    }
}
