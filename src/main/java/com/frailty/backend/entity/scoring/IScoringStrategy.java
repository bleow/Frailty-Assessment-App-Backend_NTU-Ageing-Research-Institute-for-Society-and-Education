package com.frailty.backend.entity.scoring;

import java.util.Map;

public interface IScoringStrategy {
    Double calculateScore(String answer);
    Double calcMaxScore(Map<String, Double> scoreMapping);
}
