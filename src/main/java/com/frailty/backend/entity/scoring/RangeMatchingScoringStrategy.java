package com.frailty.backend.entity.scoring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("Range_Matching")
public class RangeMatchingScoringStrategy extends ScoringStrategy {

    public RangeMatchingScoringStrategy(Map<String, Double> scoreMapping) {
        super(scoreMapping);
    }

    // we need to match interval the answer falls into based on scoreMapping
    @Override
    public Double calculateScore(String answer) {
        // we cannot guarantee that the scoreMapping is sorted.
        // verifying it is sorted takes O(n) time. sorting scoreMapping ourselves takes O(nlogn) time.
        // either way, the best case when assuming it is sorted is O(n): verifying sort O(n) + binary search O(logn),
        // so let's just assume it is not sorted and do it naively O(n)
        Double ans = Double.valueOf(answer);
        Double res = -1.0;
        for (Map.Entry<String, Double> entry : getScoreMapping().entrySet()) {
            Double key = Double.valueOf(entry.getKey());
            if (key == ans) {
                res = entry.getValue();
                break;
            }
            if (key < ans && entry.getValue() > res) {
                res = entry.getValue();
            }
        }
        return res;
    }
}
