package edu.curtin.app;

import java.util.Collections;
import java.util.List;

/**
 * HighestEstimateStrategy class implements EstimationStrategy interface,
 * provides a strategy that returns highest estimate from a list of estimates.
 */
// Reference: Strategy Pattern, Lecture Notes (Lecture 3, 2024-04-25).
public class HighestEstimateStrategy implements EstimationStrategy {
    @Override
    public int estimate(List<Integer> estimates) {
        // Returns the maximum value in the list of estimates
        return Collections.max(estimates);
    }
}
