package com.github.johanmagnusson.median;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBackedHistogram implements Histogram {

	private static final Integer ZERO = Integer.valueOf(0);

	private final Map<Integer, Integer> data;
	private int valueCount;

	public MapBackedHistogram(final int initialCapacity) {
		this.data = new HashMap<>(initialCapacity);
		this.valueCount = 0;
	}

	@Override
	public void count(final int value) {
		/*
		 * Micro-optimization, utilize Integer caching...
		 */
		final Integer ref = Integer.valueOf(value);

		/*
		 * Initialize the count for the value
		 */
		data.putIfAbsent(ref, ZERO);

		/*
		 * Count the number of occurrences of each value, incrementing it with
		 * one each time it appears
		 */
		data.compute(ref, (k, v) -> Integer.valueOf(v + 1));

		/*
		 * Track the total number of values. Can be derived from map, but this
		 * is easier.
		 */
		valueCount++;
	}

	@Override
	public int getMedian() {
		final int positionOfMedian = (valueCount - 1) / 2;
		int count = 0;

		/*
		 * Order the keys so we can traverse the key set to sum up
		 * the occurrences for each value until we reach the median.
		 */
		final List<Integer> keys = new ArrayList<>(data.keySet());
		keys.sort(Integer::compareTo);

		for (final Integer key : keys) {
			count += data.get(key);
			if (count > positionOfMedian) {
				return key;
			}
		}

		return 0;
	}
}
