package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Palindrome2 {
	
	/**
	 * Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.
	 */
	
	//@Test
	void test() {
		//assertEquals(1, minCut("aab"));
		assertEquals(3, minCut("aabcaba"));
	}
	
	
	@Test
	void longest() {
		
		assertEquals("bab", longest("babad"));
		assertEquals("bb", longest("cbbd"));
		assertEquals("a", longest("ab"));
		assertEquals("a", longest("a"));
		assertEquals("bb", longest("cbbda"));
	}
	
	
	
	/* Given a string s, return the longest palindromic substring in s*/
	private String longest(String a) {
		
		int max = 1;
		String maxPal = a.substring(0,1);
		
		for (int i = 0; i< a.length(); i++) {
			
			// while is palindrome string with middle at i and expansion on left and right, expand string
			
			//check odd size palindrome
			for(int left=i, right =i;
				left>=0 && right<a.length() &&	a.charAt(left) == a.charAt(right);
				left--,right++) {
	
					if (max < right - left +1) {
						maxPal = a.substring(left,right+1);
						max = right - left +1;
					}

			}
			

			for(int left=i, right=i+1; left>=0 && right<a.length() ; left--, right++) {
				if (a.charAt(left) == a.charAt(right)) {
					if (max < right - left +1) {
						maxPal = a.substring(left,right+1);
						max = right - left +1;
					}
				}else {
					break;
				}
			}
		}
		
		
		return maxPal;
	}
	
	
	


	public int minCut(String s) {
		
		int n,min;
	    n = s.length();
	    
	    //cut[i] represents minimum number of cuts from String 0 to i
	    int[] cut = new int[n];
	    
	    //p[i][j] represents String i to j is a palindrome or not
	    boolean[][] p = new boolean[n][n];
	    
	    for(int i=0;i<n;i++)
	    {
	        min = i;  // Max number of cuts is i for string length i+1
	        for(int j=0;j<=i;j++)
	        {
	            // Why i - j < 3  ?
	            // 1. String of length 1 is always palindrome so no need to check in boolean table
	            // 2. String of length 2 is palindrome if Ci == Cj which is already checked in first part so no need to check again
	            // 3. String of length 3 is palindrome if Ci == Cj which is already checked in first part and Ci+1 and Cj-1 is same character which is always a palindrome
	            
	            // If String length >=4
	            // then check if Ci == Cj and if they are equal check if String[j+1 .. i-1] is a palindrome from the boolean table
	            if(s.charAt(j) == s.charAt(i) && (i-j < 3 || p[j+1][i-1]))
	            {
	                // Its a palindrome as Ci == Cj and String[j+1...i-1] is a palindrome
	                p[j][i] = true; 
	                log.info("setting p[{}][{}] to true , p={}",j,i,p);
	                // j == 0 because String from j to i is a palindrome and it starts from first character so means no cuts needed
	                // Else I need a cut at jth location and it will be cuts encountered till j-1 + 1
	                min = j == 0 ? 0 : Math.min(min, cut[j-1] + 1);
	            }
	        }
	        cut[i]=min;
	        log.info("cut {}", cut);
	    }
	    log.info(" returning last cut {} means {}", cut, cut[n-1]);
	    return cut[n-1];
        
    }
}
