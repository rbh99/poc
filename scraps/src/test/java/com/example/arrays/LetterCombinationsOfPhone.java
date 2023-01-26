package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LetterCombinationsOfPhone {
	
	
	//@Test
	void test(){
		assertEquals(0,solution("").size());
		assertEquals(new HashSet<>(List.of("ad","ae","af","bd","be","bf","cd","ce","cf")), new HashSet<>(solution("23")));
		
	}

	
	//@Test
	void testRecursive(){
		//assertEquals(0,solutionRecursive("").size());
		assertEquals(new HashSet<>(List.of("ad","ae","af","bd","be","bf","cd","ce","cf")), new HashSet<>(solutionRecursive("23")));
		
	}
	
	@Test
	void testSol3(){
		//assertEquals(0,solutionRecursive("").size());
		assertEquals(new HashSet<>(List.of("ad","ae","af","bd","be","bf","cd","ce","cf")), new HashSet<>(sol3("23")));
		
	}
	
	
/**
 * Given a string containing digits from 2-9 inclusive, 
 * return all possible letter combinations that the number could represent. 
 * Return the answer in any order.

 */
	private List<String> solution(@NonNull String ph) {
		if (ph.isEmpty()) {
			return Collections.emptyList();
		}
		
		Deque<String> res =  new LinkedList<>();
		//digit mapping starting at 2
		List<String> digits = List.of("abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz");
		res.add("");
		for (int index=0; index<ph.length(); index++) {
			
			String mapped = digits.get(ph.charAt(index)-'2');
			while (res.peek().length() <= index) {
				String t = res.removeFirst();
				for (char mp: mapped.toCharArray()) {
					res.add(t+mp);
				}
			}

		}
		
		
		return new ArrayList<>(res);
	}
	
	
	
	private List<String> solutionRecursive(@NonNull String ph) {
		if (ph.isEmpty()) {
			return Collections.singletonList("");
		}
		List<String> res =  new LinkedList<>();
		List<String> digits = List.of("abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz");
		
		
		List<String> intermediate = solutionRecursive(ph.substring(1, ph.length()));
		String mapped = digits.get(ph.charAt(0)-'2');
		for (char mp: mapped.toCharArray()) {
			for(String str: intermediate) {
				res.add(mp+str);
			}
		}
		return res;
	}
	
	List<String> sol3 (String ph){
	
		List<String> result = new ArrayList<>();
		if(ph.length() == 0) return result; // Step 1
		
	    String[] letters = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"}; // Step 2
	    
	    backtrack(0, letters, ph, new StringBuilder(), result); // Step 3
	    return result; // Step 4
	}
	
	
  void backtrack(int idx, String[] letters, String ph, StringBuilder temp, List<String> result){
    if(temp.length() == ph.length()) {
    	result.add(temp.toString()); //string builder provided already contains all characters
    }
    else {
        char[] letterArr = letters[ph.charAt(idx) - '2'].toCharArray();
        for(int j = 0; j < letterArr.length; j++){
            temp.append(letterArr[j]); // add a letter from letters(index) array
            backtrack(idx + 1, letters, ph, temp, result); //add it to provided temp StringBuilder
            temp.deleteCharAt(temp.length() - 1); // remove it to be replaced with the next, if any
        }
    }
}

}
