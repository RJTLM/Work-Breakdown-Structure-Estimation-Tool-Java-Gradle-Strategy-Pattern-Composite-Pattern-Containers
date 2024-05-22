package edu.curtin.app;

import java.util.Collections;
import java.util.List;

/**
 * MedianEstimateStrategy class implements EstimationStrategy interface by providing a specific strategy,
 * which calculates median of a list of estimates
 */
// Reference: Strategy Pattern, Lecture Notes (Lecture 3, 2024-04-25).
public class MedianEstimateStrategy implements EstimationStrategy {
    @Override
    public int estimate(List<Integer> estimates) {
        Collections.sort(estimates);
        int size = estimates.size();
        // Check if  list has an odd number of elements
        if (size % 2 == 1) {
            // Return middle element for  odd sized list
            return estimates.get(size / 2);
        } else {
            // Return average of the two middle elements for even sized list
            return (estimates.get(size / 2 - 1) + estimates.get(size / 2)) / 2;
        }
    }
}
