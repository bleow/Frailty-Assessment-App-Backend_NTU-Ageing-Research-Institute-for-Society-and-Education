package com.frailty.backend.entity.scoring;

public interface IScoringStrategy {
    Double calculateScore(String answer);
    boolean isValidScore(Double score);
}
