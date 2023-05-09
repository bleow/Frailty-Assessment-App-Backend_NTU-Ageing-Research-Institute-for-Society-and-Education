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
    public void run(String... args) {
        loadPhysicalQuestionnaire();
        questionRepository.saveAll(questions);
    }

    private void loadPhysicalQuestionnaire() {
        int question_number = 0;

        Map<String, Double> scoreMapping = new HashMap<>();
        scoreMapping.put("A. Rare (less than 1 day/week)", 0.0);
        scoreMapping.put("B. Sometimes (1-2 days/week)", 0.0);
        scoreMapping.put("C. Often (3-4 days/week)", 1.0);
        scoreMapping.put("D. Most (over 5 days/week)", 1.0);
        ScoringStrategy strategy = new SingleChoiceScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.PHENOTYPE, ++question_number,
                "During the past week, I felt that everything I did was an effort", strategy));

        scoreMapping.clear();
        scoreMapping.put("A. No", 0.0);
        scoreMapping.put("B. Yes", 1.0);
        strategy = new SingleChoiceScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.PHENOTYPE, ++question_number,
                "Do you have difficulty walking up 10 stairs without resting?", strategy));

        scoreMapping.clear();
        scoreMapping.put("A. Not difficult at all", 0.0);
        scoreMapping.put("B. A bit of difficulty", 0.0);
        scoreMapping.put("C. Very difficult", 1.0);
        scoreMapping.put("D. Unable to do it at all", 1.0);
        strategy = new SingleChoiceScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.PHENOTYPE, ++question_number,
                "Do you have any difficulty walking 400m e.g. 1 lap of a running track?", strategy));

        scoreMapping.clear();
        scoreMapping.put("A. Neither 1) nor 2)", 0.0);
        scoreMapping.put("B. Only 1)", 0.0);
        scoreMapping.put("C. Only 2)", 1.0);
        scoreMapping.put("D. Both 1) and 2)", 1.0);
        strategy = new SingleChoiceScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.PHENOTYPE, ++question_number,
                "During the past week, how often did you: " +
                        "1) participate in moderate physical activities that make you breathe somewhat harder than usual, " +
                            "such as brisk walking, gardening, cleaning, bicycling at regular pace, or infant care? " +
                            "(excludes regular walking), and " +
                        "2) engage in vigorous physical activities, such as vigorous sports, " +
                            "heavy lifting, carrying items up a set of stairs, aerobics, jogging/running or fast bicycling" +
                        "?", strategy));

        scoreMapping.clear();
        scoreMapping.put("A. No", 0.0);
        scoreMapping.put("B. Yes", 1.0);
        strategy = new SingleChoiceScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.PHENOTYPE, ++question_number,
                "Was there a loss of >=5% in the last year?", strategy));
    }
}
