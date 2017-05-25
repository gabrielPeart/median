package com.github.johanmagnusson.median;

public interface Histogram {

	void count(int value);

	int getMedian();
}
