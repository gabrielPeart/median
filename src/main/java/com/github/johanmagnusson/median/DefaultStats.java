package com.github.johanmagnusson.median;

import java.util.Arrays;
import java.util.Objects;

public class DefaultStats implements Stats {

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

		/*
		 * Clone the input, we don't want to modify it. It will require more
		 * space, but it makes testing easier.
		 */
		final int[] work = validatedInput.clone();

		/*
		 * Use Java's default sort, which will use a version of quick sort.
		 * Quick sort has good performance (O(n log n) on average), but requires
		 * extra space (O(n))for keeping track of the sorted output.
		 */
		Arrays.sort(work);

		/*
		 * Fetch the median which is the middle element of the sorted list. If
		 * the number of input elements are even, the median is the average of
		 * the two middle elements but for simplicity we just choose he lower
		 * one.
		 */
		return work[((work.length - 1) / 2)];
	}
}
