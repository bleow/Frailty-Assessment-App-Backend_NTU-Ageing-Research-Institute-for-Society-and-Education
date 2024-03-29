package com.frailty.backend.entity.scoring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("Single_Choice")
public class SingleChoiceScoringStrategy extends ScoringStrategy {

    public SingleChoiceScoringStrategy(Map<String, Double> scoreMapping) {
        super(scoreMapping);
    }

    // assumes text passed in exactly matches one of the options
    @Override
    public Double calculateScore(String answer) {
        return getScoreMapping().get(answer);
    }
}
