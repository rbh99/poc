package com.example.core.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Given an array of N integers, and a number sum,
 * the task is to find the number of pairs
 * of integers in the array whose sum is equal to sum.
 */
@Slf4j
public class PairCountForSumTest {
	
	@Test
	void testCountPairs() {
		
		log.info("test started");
		assertEquals(3, countPairs( new int[]{ 1, 5, 7, -1, 5}, 6));
		assertEquals(6, countPairs( new int[]{ 1, 1, 1, 1}, 2));
	}
	
	
	@Test
	void returnMaxStringArraysTest() {
		String[] input = {"bb", "aba", "aa", "vcd", "aba"};
		assertEquals(0, Arrays.compare(new String[]{"aba", "vcd", "aba"}, returnMaxStringArraysTest(input)));
	}
	
	private String[] returnMaxStringArraysTest(String[] input) {
		
		ArrayList<String> list= new ArrayList<>();
		int max = 0;
		for (String s : input) {
			if (s.length() > max) {
				max = s.length();
				list.clear();
				list.add(s);
			} else if (s.length() == max) {
				list.add(s);
			}
		}
		return list.toArray(new String[0]);
	}


	/**
	 * main algorithm
	 *
	 *
	 */
	 int countPairs(int[]array, int sum) {

		 // map to keep record of how many times an element of the array occurs
		HashMap<Integer,Integer> valueToOccurencesMap = new HashMap<>();

		int pairs = 0;

		for (int j : array) {
			int previousPairs = valueToOccurencesMap.getOrDefault(sum - j, 0);
			if (previousPairs > 0) {
				pairs += previousPairs;
			}

			//increasing Occurrences in the map
			valueToOccurencesMap.merge(j, 1, Integer::sum);
		}
		return pairs;
	}

}
