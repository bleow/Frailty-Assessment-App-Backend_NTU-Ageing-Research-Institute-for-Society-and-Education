package com.frailty.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frailty.backend.entity.scoring.ScoringStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private QuestionType questionType;
    // https://stackoverflow.com/questions/55166779/how-to-remove-some-fields-of-an-object-in-spring-boot-response-control

    @Column(nullable = false)
    private QuestionnaireType questionnaireType;

    @Column(nullable = false)
    private Integer questionNumber;

    @Column(nullable = false)
    private String questionText;

    @OneToOne
    @JoinColumn(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ScoringStrategy strategy;

    public Question(QuestionType questionType, QuestionnaireType questionnaireType, Integer questionNumber, String questionText, ScoringStrategy strategy) {
        this.questionType = questionType;
        this.questionnaireType = questionnaireType;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.strategy = strategy;
    }
}
