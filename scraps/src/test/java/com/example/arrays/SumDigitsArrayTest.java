package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SumDigitsArrayTest {
	
	
	
/*
 Given an array of positive integers a, 
 your task is to calculate the sum of every possible a[i] ∘ a[j], 
 where a[i] ∘ a[j] is the concatenation of the string representations 
 of a[i] and a[j] respectively.

Example

    For a = [10, 2], the output should be solution(a) = 1344.
        a[0] ∘ a[0] = 10 ∘ 10 = 1010,
        a[0] ∘ a[1] = 10 ∘ 2 = 102,
        a[1] ∘ a[0] = 2 ∘ 10 = 210,
        a[1] ∘ a[1] = 2 ∘ 2 = 22.

    So the sum is equal to 1010 + 102 + 210 + 22 = 1344.

    For a = [8], the output should be solution(a) = 88.

    There is only one number in a, and a[0] ∘ a[0] = 8 ∘ 8 = 88, so the answer is 88.

    For a = [1, 2, 3], the output should be solution(a) = 198.
        a[0] ∘ a[0] = 1 ∘ 1 = 11,
        a[0] ∘ a[1] = 1 ∘ 2 = 12,
        a[0] ∘ a[2] = 1 ∘ 3 = 13,
        a[1] ∘ a[0] = 2 ∘ 1 = 21,
        a[1] ∘ a[1] = 2 ∘ 2 = 22,
        a[1] ∘ a[2] = 2 ∘ 3 = 23,
        a[2] ∘ a[0] = 3 ∘ 1 = 31,
        a[2] ∘ a[1] = 3 ∘ 2 = 32,
        a[2] ∘ a[2] = 3 ∘ 3 = 33.

    The total result is 11 + 12 + 13 + 21 + 22 + 23 + 31 + 32 + 33 = 198.

Input/Output

    [execution time limit] 3 seconds (java)

    [input] array.integer a

    A non-empty array of positive integers.

    Guaranteed constraints:
    1 ≤ a.length ≤ 105,
    1 ≤ a[i] ≤ 106.

    [output] integer64

    The sum of all a[i] ∘ a[j]s. It's guaranteed that the answer is less than 253.
 */
	@Test
	void test() {
		
		assertEquals(1344, solution(new int[]{10, 2}));
		assertEquals(88, solution(new int[]{8}));
		assertEquals(198, solution(new int[]{1, 2, 3}));
		
	}

	int solution(int[] a) {
		
		int sum = 0;
		
		for (int i=0; i<a.length; i++) {
			for (int index = 0; index<a.length;index++) {
				sum += getProduct(a[i], a[index]);
			}
		}
		
		return sum;
	}
	
	int getProduct(int l, int r) {
		return Integer.parseInt(""+l+r);
	}
	
	
}
