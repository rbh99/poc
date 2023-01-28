package com.example.core.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * You are given a string s. Consider the following algorithm applied to this string:
 *  Take all the prefixes of the string, and choose the longest palindrome between them.
 *   If this chosen prefix contains at least two characters,
 *   cut this prefix from s and go back to the first step with the updated string.
 *   Otherwise, end the algorithm with the current string s as a result.
 *
 * Your task is to implement the above algorithm and return its result when applied to string s.
 *
 * For s = "aaacodedoc", the output should be solution(s) = "".
 *  The initial string s = "aaacodedoc" contains only three prefixes which are also palindromes - "a", "aa", "aaa". The longest one between them is "aaa", so we cut if from s.
 *  Now we have string "codedoc". It contains two prefixes which are also palindromes - "c" and "codedoc".
 *  The longest one between them is "codedoc", so we cut if from the current string and obtain the empty string.
 *  Finally, the algorithm ends on the empty string, so the answer is "".
 */
@Slf4j
public class ChopStringTest {

	@Test
	void test() {

		assertEquals("",solution("aaacodedoc"));
		assertEquals("codesignal",solution("codesignal"));
	}

	/**
	 * 1. find largest prefix that is palindrome
	 * 	2. when the prefix is not palindrime and there is a palinfrime prefix, then cut the prefix that was palindrime
	 *   if no palindrime in prefix, if final string is palinddrome,cut it. else return it.
	 *
	 */
	String solution(String st) {
		
		boolean hasPrefixPalindrome = false;
		
		for (int i = 1; i<=st.length();i++) {
			String prefix = st.substring(0,i);
			//log.info("prefix " + prefix);
			if (isPalindrome(prefix) && prefix.length() > 1) {
				hasPrefixPalindrome =  true;
			}
			//cut the string if it has the palindrome in prefix
			if (i>1 && !isPalindrome(prefix) && hasPrefixPalindrome) {
				st = st.substring(i-1);
				i=0;
				hasPrefixPalindrome =  false;
			}else if (hasPrefixPalindrome && st.equals(prefix)) {
				return "";
			}
			
		}
		return st;
	}
	
	
	boolean isPalindrome (String s) {
		for (int l=0, r=s.length()-1; l<=r;l++,r--) {
			if (s.charAt(l) != s.charAt(r)) {
				return false;
			}
		}
		return true;
	}

}
