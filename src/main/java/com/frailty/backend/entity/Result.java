package com.frailty.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private QuestionType questionType;

    @Column(nullable = false)
    private Double overallScore;

    @Column(nullable = false)
    private String overallBanding;

    @OneToOne
    @JoinColumn(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AppUser appUser;

    public Result(LocalDateTime datetime, QuestionType questionType, Double overallScore, String overallBanding, AppUser appUser) {
        this.datetime = datetime;
        this.questionType = questionType;
        this.overallScore = overallScore;
        this.overallBanding = overallBanding;
        this.appUser = appUser;
    }
}
