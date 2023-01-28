package com.example.core.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
Observation: Smallest positive missing integer (SPMI) of an array of n elements has
 to be within range of 1...n if any of the elements 1...n is missing
Example: If we have 5 element array, SPMI has to be 1,2,3,4, or 5
Example2: If we have {2,3,4,5,6}, then SPMI is 1
Example3: If we have {1,2,3,4,5}, then SPMI is 6 (because 1-5 is not missing)
From the above, we can see the pattern that if an array has element values between 1 and n,
 then we can take that value and populate the value at the index which represents the value (e.g. value of 2 at index 1)
Steps:
1) Loop through the array, and take the value at index and set it at the right element
 (ignore anything outside of 1 to n)
2) Once the first loop is done,
 find the missing SPMI (again ignore anything outside of 1 to n)
 by incrementing SPMI value from 1
*/

@Slf4j
public class SmallestMissingNumberTest {
	
	@Test
	void test() {
		
		assertEquals(1, solution(new int[] {2,3,4,5,6}));
		assertEquals(3, solution(new int[] {2,0,-1,1}));
		assertEquals(2, solution(new int[] {3,0,-1,1}));
		assertEquals(3, solution(new int[] {1, 1, 2, 2}));
		
	}
	

	
	
	/*
	Steps:

    1) Loop through the array, and take the value at index and set it at the right element
    (ignore anything outside of 1 to n)
    2) Once the first loop is done, find the missing SPMI
    (again ignore anything outside of 1 to n) by incrementing SPMI value from 1


    index 0 -> value 1
    index 1 -> value 2
    ...
    */
    public int solution(int[] nums) {
        // step 1
        debug("Input: ",nums);
        for(int index = 0; index < nums.length; ) {
            int valueForIndex = nums[index];
            //If current value is at the correct index or is not 1...n, we move to next item 
            if(isValueAtCorrectIndex(valueForIndex, index) || !isValidMinNumber(valueForIndex,nums)) {
                index++;
            } else {
                boolean swapHasEffect = swap(nums,index,valueForIndex-1);
                //If input has dups the swap would not do anything and we get into infinite loop (e.g {1,1})
                //so we move forward to next item
                if(!swapHasEffect) {
                    index++;
                }
            }
        }
        //step 2: we should have the numbers in order now, let alone the missing one
        debug("After Swapping: ",nums);

        int spmi = 1;
        for(int i = 0; i < nums.length; i++) {
            int value = nums[i];
            if(isValueAtCorrectIndex(value, i)) {
                spmi++;                
            } else {
                return spmi;
            }
         }
         return spmi;        
    }

    /**
     * returns true if value -1 is equal to index
     */
    boolean isValueAtCorrectIndex(int value, int index) {
        return value-1 == index;
    }

    /**
     * returns true of aValue is positive and less than nums length
     */
    boolean isValidMinNumber(int aValue, int[] nums) {
        return aValue > 0 && aValue <= nums.length;
    } 
    
    void debug(String comment, int[] nums) {
        System.out.println(comment + Arrays.toString(nums));
    }
    
     /**
     * returns true if swapped and values were actually changed
     */
    boolean swap(int[] ints, int from, int to) {
        if(ints[from] == ints[to]) {
            return false;
        }
        
        int temp = ints[from];
        ints[from] = ints[to];
        ints[to] = temp;
        return true;
    }

}
