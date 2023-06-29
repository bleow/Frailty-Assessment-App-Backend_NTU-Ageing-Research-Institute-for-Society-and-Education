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
        loadPhysicalPhenotype();
        loadPhysicalIPAQ();
        loadPhysicalSARC();
    }

    private void loadPhysicalPhenotype() {
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

    private void loadPhysicalIPAQ() {
        int question_number = 0;

        Map<String, Double> scoreMapping = new HashMap<>();
        scoreMapping.put("999", 1.0);
        scoreMapping.put("0", 0.0);
        ScoringStrategy strategy = new RangeMatchingScoringStrategy(scoreMapping);
        scoringStrategyRepository.save(strategy);
        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.IPAQ, ++question_number,
                "The first question is about the time you spent sitting on weekdays during the last 7 days. Include time spent at work, at home, while doing course work and during leisure time. This may include time spent sitting at a desk, visiting friends, reading, or sitting or lying down to watch television. \n" +
                        "\n" +
                        "During the last 7 days, how much time did you spend sitting on a week day? \n", strategy));

        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.IPAQ, ++question_number,
                "Think about the time you spent walking in the last 7 days. This includes at work and at home, walking to travel from place to place, and any other walking that you have done solely for recreation, sport, exercise, or leisure. \n" +
                        "\n" +
                        "During the last 7 days, on how many days did you walk for at least 10 minutes at a time? \n" +
                        "\n" +
                        "How much time did you usually spend walking on one of those days? \n", strategy));

        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.IPAQ, ++question_number,
                "During the last 7 days, on how many days did you do moderate physical activities like gardening, cleaning, bicycling at a regular space, swimming or other fitness activities. \n" +
                        "\n" +
                        "Think only about those physical activities that you did for at least 10 minutes at a time. Do not include walking.\n" +
                        "\n" +
                        "How much time did you usually spend doing moderate physical activities on one of those days?\n", strategy));

        questions.add(new Question(QuestionType.PHYSICAL, QuestionnaireType.IPAQ, ++question_number,
                "During the last 7 days, on how many days did you do vigorous physical activities like heavy lifting, heavier garden or construction work, chopping words, aerobics, jogging/running of fast bicycling? \n" +
                        "\n" +
                        "Think only about those physical activities that you did for at least 10 minutes at a time. \n" +
                        "\n" +
                        "How much time did you usually spend doing vigorous physical activities on one of those days?\n", strategy));
    }

    private void loadPhysicalSARC() {
    }

}
