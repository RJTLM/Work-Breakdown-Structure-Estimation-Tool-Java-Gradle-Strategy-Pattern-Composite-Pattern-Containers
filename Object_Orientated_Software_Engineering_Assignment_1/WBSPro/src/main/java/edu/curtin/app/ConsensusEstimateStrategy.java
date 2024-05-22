package edu.curtin.app;

import java.util.List;

/**
 * ConsensusEstimateStrategy implements EstimationStrategy interface,
 * using the arithmetic average as a means of reaching a consensus among estimates,
 * which is then rounded down to the nearest integer.
 */
// Reference: Strategy Pattern, Lecture Notes (Lecture 3, 2024-04-25).
public class ConsensusEstimateStrategy implements EstimationStrategy {
    @Override
    public int estimate(List<Integer> estimates) {
        // Calculate average and return
        return (int) estimates.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
}
