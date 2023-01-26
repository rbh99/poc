package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrappingRainTest {

	/**
	 * Given n non-negative integers representing an elevation map where the width
	 * of each bar is 1, compute how much water it can trap after raining.
	 */
	@Test
	void test() {

		assertEquals(6, solution(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));
		assertEquals(9, solution(new int[] {4,2,0,3,2,5}));
		
		assertEquals(6, sol2Pointers(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));
		assertEquals(9, sol2Pointers(new int[] {4,2,0,3,2,5}));
		
		
		
	}
	
	
	int sol2Pointers(int[] height) {
		
		if(height.length < 3) return 0;
		int sum = 0;
		
		int left = 0;
		int right = height.length-1;
		int leftHeight = height[left];
		int rightHeight= height[right];
		
		while (left<=right) {
			
			if (leftHeight <= rightHeight) {
				leftHeight = Math.max(leftHeight, height[left]);
				sum+= leftHeight- height[left];
				left++;
			}else {
				rightHeight=Math.max(rightHeight, height[right]);
				sum+=rightHeight-height[right];
				right--;
			}

		}
		
		return sum;
	}
	
	
	
	

	private int solution(int[] height) {
		
		if(height.length < 3) return 0;

		int sum = 0;

		 int leftHeight = 0;
	        
	        //find next local min
	        for(int k=0;k<height.length; k++){
	            
	            if (height[k]>=leftHeight){
	            	leftHeight=height[k];
	            }else {
	                //find next local
	            	boolean found = false;
	                
	            	for (int j=k+1;j<height.length;j++){
	                    if (height[j] >= leftHeight){
	                        //fill between leftHeight at k and j
	                        sum+=fillBetween (k,j,Math.min(leftHeight,height[j]), height);
	                        k=j;
	                        leftHeight= height[k];
	                        found = true;
	                        break;
	                    }
	                }
	                if(!found )
	                	leftHeight = height[k];
	            }
	            
	        }
		
		
		return sum;
	}

	
	int fillBetween(int min, int max, int height, int[] arr){
		
		int sum=0;
		for (int i=min; i<=max; i++) {
			if (height>arr[i]) sum+=height-arr[i];
		}
		return sum;
	}
	
	
}
