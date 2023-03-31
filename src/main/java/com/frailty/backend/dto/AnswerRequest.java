package com.frailty.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record AnswerRequest(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime datetime,
        Integer questionNumber,
        String answerText
) {
        public String answerTextToString() {
                return String.join(", ", answerText);
        }
}
