package com.github.johanmagnusson.median;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class StatsTest {

	private static final int[] SAMPLE_ONE = {4, 1, 6, 3, 7, 8, 7};
	private static final int[] SAMPLE_TWO = {1, 1, 1, 2, 3, 3, 3};
	private static final int[] SAMPLE_THREE = {1000, 1000, 1000};
	private static final int[] SAMPLE_FOUR = {1, 2, 3, 2000, 2000};
	private static final int[] SAMPLE_FIVE = {1, 2000, 2000};
	private static final int[] SAMPLE_SIX = { 14, 81, 59, 17, 80, 53, 77, 93, 48, 50, 18, 83, 6, 31, 10, 74, 83, 70, 98,
			69, 31, 88, 37, 98, 9, 91, 9, 10, 99, 78, 31, 56, 83, 71, 90, 51, 22, 59, 44, 76, 18, 29, 58, 87, 84, 39,
			73, 68, 85, 55, 91, 70, 51, 54, 26, 98, 94, 87, 8, 27, 14, 87, 86, 44, 65, 98, 69, 57, 17, 75, 70, 62, 55,
			78, 59, 98, 66, 68, 21, 28, 39, 15, 46, 90, 92, 30, 30, 33, 51, 71, 2, 10, 13, 62, 55, 39, 14, 96, 48, 94 };
	private static final int[] SAMPLE_SEVEN = { 232, 782, 477, 786, 215, 549, 771, 499, 331, 88, 973, 784, 1, 917, 278,
			62, 194, 687, 432, 253, 165, 356, 910, 199, 537, 770, 610, 567, 117, 816, 341, 553, 683, 907, 808, 809, 962,
			939, 546, 976, 452, 44, 581, 205, 972, 164, 580, 378, 496, 717, 338, 66, 648, 290, 810, 988, 644, 810, 301,
			318, 532, 859, 802, 767, 223, 646, 608, 427, 877, 400, 268, 929, 992, 216, 507, 868, 623, 425, 704, 562,
			743, 283, 255, 115, 528, 558, 615, 245, 764, 244, 311, 324, 807, 479, 71, 227, 296, 471, 51, 96, 542, 564,
			592, 431, 668, 255, 169, 15, 844, 91, 719, 327, 917, 613, 770, 994, 272, 39, 708, 448, 420, 740, 27, 191,
			59, 456, 958, 884, 96, 870, 164, 271, 281, 466, 826, 915, 234, 931, 141, 553, 50, 528, 198, 207, 230, 281,
			944, 636, 920, 754, 259, 840, 274, 215, 928, 658, 376, 662, 604, 52, 984, 473, 255, 444, 356, 150, 84, 394,
			284, 7, 558, 950, 163, 248, 47, 151, 244, 619, 842, 304, 739, 109, 97, 497, 133, 847, 969, 672, 471, 763,
			540, 947, 460, 330, 145, 508, 616, 70, 945, 622, 991, 448, 785, 533, 482, 68, 966, 326, 22, 934, 453, 289,
			510, 383, 800, 125, 766, 235, 263, 551, 30, 960, 32, 412, 11, 411, 338, 534, 552, 143, 277, 175, 329, 222,
			292, 226, 826, 837, 647, 925, 565, 430, 839, 676, 279, 382, 837, 34, 392, 770, 585, 688, 110, 224, 670, 690,
			535, 437, 545, 904, 621, 291, 818, 685, 770, 350, 795, 736, 423, 92, 375, 331, 365, 704, 459, 467, 148, 907,
			13, 848, 873, 755, 927, 925, 614, 66, 181, 872, 236, 101, 690, 998, 768, 303, 164, 734, 875, 159, 933, 212,
			961, 996, 189, 850, 485, 905, 327, 192, 422, 635, 657, 880, 788, 256, 152, 695, 699, 373, 438, 50, 402, 766,
			691, 393, 363, 615, 245, 841, 249, 15, 45, 894, 552, 110, 293, 205, 143, 652, 398, 777, 154, 832, 49, 186,
			475, 188, 877, 696, 362, 611, 597, 204, 243, 26, 631, 676, 719, 886, 271, 194, 375, 302, 858, 443, 563, 302,
			945, 596, 125, 803, 615, 422, 31, 573, 106, 566, 462, 768, 912, 919, 753, 983, 348, 903, 259, 347, 365, 956,
			877, 241, 268, 193, 997, 371, 706, 671, 241, 590, 398, 714, 750, 802, 642, 605, 284, 469, 507, 292, 537,
			686, 403, 994, 592, 3, 294, 382, 504, 239, 199, 322, 545, 112, 923, 625, 720, 615, 998, 533, 90, 828, 13,
			272, 516, 888, 285, 10, 852, 392, 118, 156, 461, 927, 356, 936, 687, 135, 982, 849, 626, 879, 395, 407, 3,
			811, 410, 220, 206, 823, 785, 766, 993, 271, 916, 467, 829, 722, 714, 530, 311, 289, 811, 365, 371, 669,
			511, 699, 810, 867, 125, 31, 540, 345, 657, 61, 99, 436, 339, 912, 894, 877, 16, 472, 666, 695, 22, 398,
			180, 499, 439, 102, 941, 326, 527, 914, 841, 935, 98, 382, 528, 93, 664, 669, 778, 330, 243, 491, 199, 706,
			715, 500, 213, 974, 140, 836, 633, 930, 498, 565, 96, 838, 992, 125, 572, 145, 886, 147, 856, 856, 855, 507,
			856, 448, 190, 427, 677, 167, 84, 51, 225, 270, 564, 625, 526, 84, 374, 769, 579, 665, 669, 602, 948, 219,
			29, 904, 407, 101, 388, 393, 695, 236, 928, 693, 191, 204, 786, 390, 275, 838, 40, 501, 258, 925, 57, 989,
			460, 529, 740, 844, 14, 190, 37, 179, 658, 570, 72, 298, 227, 139, 389, 295, 843, 273, 393, 86, 452, 954,
			957, 880, 700, 659, 390, 75, 678, 147, 985, 119, 992, 804, 287, 801, 296, 822, 221, 360, 604, 680, 429, 627,
			812, 561, 46, 440, 608, 603, 885, 47, 773, 981, 449, 698, 286, 728, 850, 625, 611, 386, 37, 531, 520, 810,
			550, 778, 121, 706, 661, 817, 955, 697, 808, 586, 693, 395, 689, 729, 781, 744, 799, 345, 573, 244, 792,
			753, 508, 402, 999, 301, 306, 277, 316, 871, 920, 661, 559, 147, 780, 434, 176, 806, 981, 462, 752, 172,
			305, 386, 747, 945, 314, 190, 472, 605, 704, 311, 574, 677, 357, 157, 991, 952, 488, 811, 264, 519, 679,
			377, 828, 224, 131, 680, 363, 798, 495, 897, 146, 749, 941, 857, 811, 100, 864, 637, 742, 639, 830, 5, 799,
			387, 187, 982, 451, 691, 971, 35, 706, 520, 394, 880, 867, 411, 325, 454, 253, 20, 266, 688, 555, 507, 372,
			556, 354, 920, 446, 924, 316, 407, 535, 240, 582, 407, 450, 483, 347, 179, 450, 468, 139, 89, 862, 428, 575,
			784, 693, 359, 675, 241, 4, 321, 591, 577, 947, 526, 697, 163, 60, 528, 340, 389, 595, 724, 310, 128, 893,
			84, 56, 990, 428, 689, 397, 208, 360, 155, 805, 573, 967, 391, 235, 419, 921, 978, 843, 975, 13, 34, 876,
			376, 790, 964, 253, 542, 428, 790, 456, 26, 98, 456, 238, 564, 679, 144, 186, 707, 1, 768, 113, 771, 734,
			442, 36, 961, 914, 7, 810, 744, 964, 768, 155, 461, 952, 32, 202, 140, 31, 10, 2, 878, 232, 951, 481, 531,
			234, 68, 976, 280, 841, 702, 456, 838, 674, 733, 853, 155, 850, 293, 789, 248, 446, 327, 41, 434, 704, 67,
			823, 61, 648, 728, 736, 133, 487, 188, 521, 928, 611, 379, 100, 595, 836, 995, 856, 820, 292, 321, 503, 276,
			276, 684, 637, 22, 980, 164, 557, 560, 811, 884, 667, 784, 393, 600, 6, 357, 4, 419, 837, 917, 431, 707,
			278, 548, 158, 676, 371, 955, 924, 825, 227, 137, 876, 81, 9, 350, 688, 453, 769, 274, 535, 595, 33, 295,
			703, 617, 485, 399, 466, 554, 411, 155, 385, 127, 620, 490, 598, 518, 551, 701, 385, 674, 433, 863, 377,
			407, 703, 392, 415, 124, 703, 119, 552, 967, 663, 299, 343, 484, 753, 548, 678, 326, 339, 698, 277, 597,
			764, 911 };

	private Stats stats;

	@Before
	public void setup() {
		stats = new HistogramStats(new ArrayBackedHistogram(2000));
	}

	@Test
	public void verifySampleOne() {
		final int result = stats.calculateMedian(SAMPLE_ONE);
		assertEquals(6, result);
	}

	@Test
	public void verifySampleTwo() {
		final int result = stats.calculateMedian(SAMPLE_TWO);
		assertEquals(2, result);
	}

	@Test
	public void verifySampleThree() {
		final int result = stats.calculateMedian(SAMPLE_THREE);
		assertEquals(1000, result);
	}

	@Test
	public void verifySampleFour() {
		final int result = stats.calculateMedian(SAMPLE_FOUR);
		assertEquals(3, result);
	}

	@Test
	public void verifySampleFive() {
		final int result = stats.calculateMedian(SAMPLE_FIVE);
		/*
		 * Note: expected result in assignment appears to be wrong, lists median as 1000, should be 2000
		 */
		assertEquals(2000, result);
	}

	@Test
	public void verifySampleSix() {
		final int result = stats.calculateMedian(SAMPLE_SIX);
		assertEquals(58, result);
	}

	@Test
	public void verifySampleSeven() {
		final int result = stats.calculateMedian(SAMPLE_SEVEN);
		assertEquals(507, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnEmptyInput() {
		stats.calculateMedian(new int[]{});
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowExceptionOnNullInput() {
		stats.calculateMedian(null);
	}

	@Test
	public void shouldHandleSingleElementInput() {
		final int result = stats.calculateMedian(new int[]{17});
		assertEquals(17, result);
	}

	@Test
	@Ignore
	public void shouldHandleLargeValues() {
		final Random r = new Random(19);
		final int[] input = IntStream
				.generate(() -> r.nextInt(1001))
				.limit(100_000_000)
				.toArray();

		bench(input, new DefaultStats());
		bench(input, new HistogramStats(new ArrayBackedHistogram(1000)));
		bench(input, new HistogramStats(new MapBackedHistogram(1000)));
	}

	private void bench(final int[] input, final Stats stats) {
		final long start = System.currentTimeMillis();
		int defaultMedian = stats.calculateMedian(input);
		final long stop = System.currentTimeMillis();
		System.out.println(String.format(
				"Calculated median %d with %s in %s ms",
				defaultMedian,
				stats.getClass().getSimpleName(),
				stop - start));
	}
}
