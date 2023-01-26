package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinNumberTest {
	
	@Test
	void test() {
		
		//assertEquals(1, solution(new int[] {2,3,4,5,6}));
		//assertEquals(3, solution(new int[] {2,0,-1,1}));
		assertEquals(2, solution(new int[] {3,0,-1,1}));
		//assertEquals(3, solution(new int[] {1, 1, 2, 2}));
		
	}
	

	
	
	//Observation: Smallest positive missing integer (SPMI) of an array of n elements has to be within range of 1...n if any of the elements 1...n is missing
    //Example: If we have 5 element array, SPMI has to be 1,2,3,4, or 5 
    //Example2: If we have {2,3,4,5,6}, then SPMI is 1
    //Example3: If we have {1,2,3,4,5}, then SPMI is 6 (because 1-5 is not missing)
    //From the above, we can see the pattern that if an array has element values between 1 and n, then we can take that value and popolate the value at the index which represents the value (e.g. value of 2 at index 1) 
    //Steps:
    //1) Loop through the array, and take the value at index and set it at the right element (ignore anything outside of 1 to n)
    //2) Once the first loop is done, find the missing SPMI (again ignore anything outside of 1 to n) by incrementing SPMI value from 1 
    public int solution(int[] nums) {
        debug("Input: ",nums);
        for(int i = 0; i < nums.length; ) {
            int currVal = nums[i];
            //If current value is at the correct index or is not 1...n, we move to next item 
            if(isValueAtCorrectIndex(currVal, i) || !isValidMinNumber(currVal,nums)) {
                i++;
            } else {
                boolean swapHasEffect = swap(nums,i,currVal-1);
                //If input has dups the swap would not do anything and we get into infinite loop (e.g {1,1})
                //so we move forward to next item
                if(!swapHasEffect) {
                    i++;
                }
            }
        }
        debug("After Swapping: ",nums);
        int spmi = 1;
        for(int i = 0; i < nums.length; i++) {
            int currVal = nums[i];
            if(isValueAtCorrectIndex(currVal,i)) {
                spmi++;                
            } else {
                return spmi;
            }
                
         }
         return spmi;        
    }
    
    boolean isValueAtCorrectIndex(int value, int index) {
        return value-1 == index;
    }
    
    boolean isValidMinNumber(int aValue, int[] nums) {
        return aValue > 0 && aValue <= nums.length;
    } 
    
    void debug(String comment, int[] nums) {
    	//Arrays.stream(nums).boxed().collect(Collectors.toList())
        System.out.println(comment + Arrays.toString(nums));
    }
    
    //If swap actually changes values of the elements return true
    boolean swap(int[] nums, int from, int to) {
        if(nums[from] == nums[to]) {
            return false;
        }
        
        int temp = nums[from];
        nums[from] = nums[to];
        nums[to] = temp;
        return true;
    }

}
