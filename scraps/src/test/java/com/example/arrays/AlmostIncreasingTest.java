package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlmostIncreasingTest {

	/*
	 Given a sequence of integers as an array, determine whether it is possible to obtain a strictly increasing sequence by removing no more than one element from the array.

Note: sequence a0, a1, ..., an is considered to be a strictly increasing if a0 < a1 < ... < an. Sequence containing only one element is also considered to be strictly increasing.

Example

    For sequence = [1, 3, 2, 1], the output should be
    solution(sequence) = false.

    There is no one element in this array that can be removed in order to get a strictly increasing sequence.

    For sequence = [1, 3, 2], the output should be
    solution(sequence) = true.

    You can remove 3 from the array to get the strictly increasing sequence [1, 2]. Alternately, you can remove 2 to get the strictly increasing sequence [1, 3].

Input/Output

    [execution time limit] 3 seconds (java)

    [input] array.integer sequence

    Guaranteed constraints:
    2 ≤ sequence.length ≤ 105,
    -105 ≤ sequence[i] ≤ 105.

	 */
	
	
	@Test
	void test() {
		assertTrue(solution(new int[] {1, 2, 3, 4, 3, 6}));
		assertFalse(solution(new int[]{1, 2, 1, 2}));
		
		assertFalse(solution(new int[]{1, 3, 2, 1}));
		
		assertTrue(solution(new int[]{1, 3, 2}));
		
		assertTrue(solution(new int[]{1, 3, 5, 4, 5}));
		
		assertFalse(solution(new int[]{3, 6, 5, 8, 10, 20, 15}));
		assertTrue(solution(new int[] {10, 1, 2, 3, 4, 5}));
		
	}
	
	boolean solution(int[] sequence) {

	    int countToRemove = 0;
	    for (int i=1;i<sequence.length;i++){

	        if (sequence[i] <= sequence[i-1])
	        {
	           countToRemove++;           
	           if (i< sequence.length -1 && sequence[i-1] >= sequence[i+1]
	        		   && i>1 && sequence[i-2] >=sequence[i]
	        		   ) return false;
	        }
	    
	    }

	    return countToRemove <= 1;
	}


	
}
