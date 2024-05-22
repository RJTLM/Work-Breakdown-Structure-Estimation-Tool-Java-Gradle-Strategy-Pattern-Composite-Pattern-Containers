package edu.curtin.app;

import java.util.List;

/**
 * EstimationStrategy interface defines a common contract for all estimation strategies,
 * requires  implementation of estimate method that takes a list of integer estimates.
 */
// This class uses the Strategy Pattern to handle different effort estimation strategies.
// Reference: Strategy Pattern, Lecture Notes (Lecture 2, 2024-04-24)
public interface EstimationStrategy {
    int estimate(List<Integer> estimates);
}
