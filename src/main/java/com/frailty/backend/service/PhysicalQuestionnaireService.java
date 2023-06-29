package com.frailty.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.frailty.backend.dto.AnswerRequest;
import com.frailty.backend.dto.QuestionDTO;
import com.frailty.backend.dto.QuestionnaireDTO;
import com.frailty.backend.entity.*;
import com.frailty.backend.entity.scoring.ScoringStrategy;
import com.frailty.backend.mappers.QuestionListToDTOListMapper;
import com.frailty.backend.output.Localiser;
import com.frailty.backend.repository.AnswerRepository;
import com.frailty.backend.repository.QuestionRepository;
import com.frailty.backend.repository.ResultRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PhysicalQuestionnaireService {
    public PhysicalQuestionnaireService(Localiser localiser,
                                        QuestionRepository questionRepository,
                                        AnswerRepository answerRepository,
                                        ResultRepository resultRepository,
                                        AppUserService appUserService) {
        this.localiser = localiser;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.resultRepository = resultRepository;
        this.appUserService = appUserService;
        this.mapper = Mappers.getMapper(QuestionListToDTOListMapper.class);
    }

    private Localiser localiser;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private ResultRepository resultRepository;
    private AppUserService appUserService;
    private QuestionListToDTOListMapper mapper;

    public QuestionnaireDTO getQuestions(String typeStr) {
        QuestionnaireType type = QuestionnaireType.valueOf(typeStr.toUpperCase());
        List<Question> physicalQuestions = questionRepository.findByQuestionTypeAndQuestionnaireType(QuestionType.PHYSICAL, type);
        String questionType = String.valueOf(physicalQuestions.stream().map(Question::getQuestionType).findFirst().get());
        String questionnaireType = String.valueOf(physicalQuestions.stream().map(Question::getQuestionnaireType).findFirst().get());
        List<QuestionDTO> questions = mapper.mapQuestionListToDTOList(physicalQuestions);
        QuestionnaireDTO res = new QuestionnaireDTO(questionType, questionnaireType, questions);
        return res;
    }

    public String postAnswers(String typeStr, String username, List<AnswerRequest> answerRequest) {
        QuestionnaireType type = QuestionnaireType.valueOf(typeStr.toUpperCase());
        log.debug("==TESTING== USERNAME {}", username);
        AppUser appUser = appUserService.getValidUser(username);
        ArrayList<Answer> answers = new ArrayList<>();
        Double totalScore = 0.0;
        Double maxScore = 0.0;
        for (AnswerRequest request : answerRequest) {
            Question question = questionRepository.findByQuestionNumberAndQuestionTypeAndQuestionnaireType(request.questionNumber(), QuestionType.PHYSICAL, type).orElseThrow(() -> new IllegalStateException(localiser.notFound("Question ID", request.questionNumber().toString())));
            ScoringStrategy strategy = question.getStrategy();
            log.info("Question is {} {}", question.getQuestionText(), strategy.getScoreMapping().toString());
            Double answerScore = strategy.calculateScore(request.answerText());
            maxScore += strategy.getMaxScore();
            totalScore += answerScore;
            Answer answer = new Answer(appUser, request.datetime(), question, request.answerTextToString(), answerScore);
            answers.add(answer);
        }
        answerRepository.saveAll(answers);

        LocalDateTime resultsDateTime = answers.stream().map(Answer::getDatetime).min(LocalDateTime::compareTo).get();
        String overallBanding = "ERROR: question type not recognised";
        if (type == QuestionnaireType.PHENOTYPE) {
            if (totalScore <= 1) {
                overallBanding = "Non-frail";
            } else if (totalScore <= 3) {
                overallBanding = "Pre-frail";
            } else {
                overallBanding = "Frail";
            }
        } else if (type == QuestionnaireType.IPAQ) {
            overallBanding = "Non-frail (TODO: the scoring system has not been implemented yet)";
        }

        Result res = new Result(resultsDateTime, QuestionType.SOCIAL, type, totalScore, overallBanding, appUser);
        log.info("New quiz of type {}, {} was finished at {} by {}. Results: {}, raw score: {}",
                res.getQuestionType(), res.getQuestionnaireType(), res.getDatetime(), res.getAppUser(), res.getOverallBanding(), res.getOverallScore());
        resultRepository.save(res);

        return res.getOverallBanding();
    }

    public List<Answer> getAnswers(String username, String email) {
        AppUser clinician = appUserService.getValidUser(username);
        AppUser patient = appUserService.getValidUser(email);
        return answerRepository.findByAppUser(patient);
    }
}
