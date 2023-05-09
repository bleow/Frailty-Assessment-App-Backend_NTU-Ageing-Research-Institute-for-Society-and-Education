package com.frailty.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionnaireDTO {
    private String questionType;
    private String questionnaireType;
    private List<QuestionDTO> questions;
}