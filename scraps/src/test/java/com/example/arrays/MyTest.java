package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyTest {
	
	//@Test
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
		for (int i=0; i< input.length; i++) {
			if (input[i].length() > max) {
				max = input[i].length();
				list.clear();
				list.add(input[i]);
			}else if (input[i].length() == max) {
				list.add(input[i]);
			}
		}
		return list.toArray(new String[0]);
	}
	
	
	/**
	 * Given an array of N integers, and a number sum, the task is to find the number of pairs 
	 * of integers in the array whose sum is equal to sum.
	 */
	private static int countPairs(int[]array, int sum) {
		
		HashMap<Integer,Integer> frequency = new HashMap<>();
		int count = 0;
		
		for (int i=0; i<array.length;i++) {
			
			//
			int previousPairs = frequency.getOrDefault(sum - array[i], 0);
			if (previousPairs>0) {
				count+=previousPairs;
			}

			//frequency.put(array[i], frequency.getOrDefault(sum - array[i], 0)+1);
			frequency.merge(array[i], 1, (v1 ,val) -> v1+val);
		}
		return count;
	}

}
