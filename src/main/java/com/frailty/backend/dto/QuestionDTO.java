package com.frailty.backend.dto;

import com.frailty.backend.entity.scoring.ScoringStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Integer id;
    private Integer questionNumber;
    private String questionText;

    private ScoringStrategy strategy;
}
