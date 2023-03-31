package com.frailty.backend.config;

import com.frailty.backend.entity.Question;
import com.frailty.backend.entity.QuestionType;
import com.frailty.backend.entity.QuestionnaireType;
import com.frailty.backend.entity.scoring.*;
import com.frailty.backend.repository.QuestionRepository;
import com.frailty.backend.repository.ScoringStrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuestionSeeder implements CommandLineRunner {
    private ArrayList<Question> questions;

    public QuestionSeeder() {
        questions = new ArrayList<Question>();
    }

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ScoringStrategyRepository scoringStrategyRepository;

    @Override
    public void run(String... args) throws Exception {
        loadPhysicalQuestionnaire();
        questionRepository.saveAll(questions);
    }

    private void loadPhysicalQuestionnaire() {
        Map<String, Double> scoreMapping = new HashMap<>();
        scoreMapping.put("A. Rare (less than 1 day/week)", 0.0);
        scoreMapping.put("B. Sometimes (1-2 days/week)", 0.0);
        scoreMapping.put("C. Often (3-4 days/week)", 1.0);
        scoreMapping.put("D. most (over 5 days/week)", 1.0);
        ScoringStrategy strategy = new SingleChoiceScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.PHENOTYPE, 1, "During the past week, I felt that everything I did was an effort", strategy));
    }
}
