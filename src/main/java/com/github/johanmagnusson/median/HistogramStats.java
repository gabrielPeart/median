package com.github.johanmagnusson.median;

import java.util.Objects;

/**
 * Implementation of Stats that use a Histogram to track the input values.
 *
 * As we go through the values, in order, we can keep track of how many values
 * we have processed, once we reach the point past the index of our median
 * value, we are finished.
 *
 * This way, we have an O(n) time complexity (need to go through all elements
 * once, plus the work array) and a space complexity bound to the variance of
 * values (O(m)).
 */
public class HistogramStats implements Stats {

	/**
	 * Setup a histogram of the values we encounter.
	 */
	private final Histogram histogram;

	public HistogramStats(final Histogram histogram) {
		this.histogram = histogram;
	}

	@Override
	public int calculateMedian(final int[] input) {
		/*
		 * Validate input, makes tracing easier if NPE exception appears here.
		 */
		final int[] validatedInput = Objects.requireNonNull(input, "Input array can not be null");

		/*
		 * Guard for empty input. Opted for exception here, 0 is not necessarily
		 * a good default.
		 */
		if (validatedInput.length == 0) {
			throw new IllegalArgumentException("Input is empty, can't calculate a median");
		}

		for (final int value : validatedInput) {
			histogram.count(value);
		}

		return histogram.getMedian();
	}
}
