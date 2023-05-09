package com.frailty.backend.entity.scoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "scoring_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ScoringStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // https://stackoverflow.com/questions/55166779/how-to-remove-some-fields-of-an-object-in-spring-boot-response-control

    private Integer id;

    @Column(nullable = false)
    private Double maxScore;

    @ElementCollection
    private Map<String, Double> scoreMapping;

    public ScoringStrategy(Double maxScore, Map<String, Double> scoreMapping) {
        this.maxScore = maxScore;
        this.scoreMapping = scoreMapping;
    }
    public abstract Double calculateScore(String answer);
    public boolean isValidScore(Double score) {
        return score <= this.maxScore;
    };
}
