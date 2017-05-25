package com.github.johanmagnusson.median;

public class ArrayBackedHistogram implements Histogram {

	private final int[] data;
	private final int upperBound;
	private int valueCount;

	public ArrayBackedHistogram(final int upperBound) {
		this.data = new int[upperBound + 1];
		this.upperBound = upperBound;
		this.valueCount = 0;
	}

	@Override
	public void count(final int value) {
		if (value < 0 || value > upperBound) {
			throw new IllegalArgumentException(
					String.format(
							"Input values required to be in the range of 0 to %d, inclusive",
							upperBound));
		}

		data[value]++;
		valueCount++;
	}

	@Override
	public int getMedian() {
		final int positionOfMedian = (valueCount - 1) / 2;
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			count += data[i];
			if (count > positionOfMedian) {
				return i;
			}
		}

		return 0;
	}
}
