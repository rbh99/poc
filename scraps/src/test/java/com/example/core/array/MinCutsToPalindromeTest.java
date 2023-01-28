package com.example.core.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * Print the minimum cuts needed for a palindrome partitioning of s.
 */
@Slf4j
public class MinCutsToPalindromeTest {


    @Test
    void minimumCuts_success() {
        //assertEquals(1, minCut("aab"));
        assertEquals(3, minCut("aabcaba"));
    }


    @Test
    void longestPalindromeInString_success() {

        assertEquals("bab", longestPalindromeInStringTest("babad"));
        assertEquals("bb", longestPalindromeInStringTest("cbbd"));
        assertEquals("a", longestPalindromeInStringTest("ab"));
        assertEquals("a", longestPalindromeInStringTest("a"));
        assertEquals("bcb", longestPalindromeInStringTest("abcbbda"));
    }


    /* Given a string s, return the longest palindromic substring in s
    * Algorithm:
    * from index starting at index 0 to string length
    * expand string while string with middle at i and expansion on left and right is palindrome
    * and check odd and even sized Strings centered at i
    * */
    private String longestPalindromeInStringTest(String a) {

        String maxPal = a.substring(0, 1);
        int max = maxPal.length();

        for (int i = 0; i < a.length(); i++) {
            //check odd size palindrome
            for (int left = i, right = i;
                 left >= 0 && right < a.length() && a.charAt(left) == a.charAt(right);
                 left--, right++) {

                if (max < right - left + 1) {
                    maxPal = a.substring(left, right + 1);
                    max = right - left + 1;
                }
            }

            // check even size palindrome
            for (int left = i, right = i + 1;
                 left >= 0 && right < a.length() && a.charAt(left) == a.charAt(right);
                 left--, right++) {

                if (max < right - left + 1) {
                    maxPal = a.substring(left, right + 1);
                    max = right - left + 1;
                }

            }
        }
        return maxPal;
    }


    /**
     * returns minimum cuts to divide a String s into Palindromes
     * Algorithm:
     *
     */
    public int minCut(String s) {
        log.info("checking minimum cuts for String {}", s);
        var n = s.length();
        int min;

        //minCuts[i] represents minimum number of cuts from String 0 to i
        int[] minCuts = new int[n];

        //p[i][j] represents String i to j is a palindrome or not
        boolean[][] p = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            log.info("==== i = {}====", i);
            min = i;  // Max number of cuts is i for string length i+1

            //for substrings 0... i, check palindromes. as we incremented i only by 1, we check only
            //a limited number of possibilities
            for (int j = 0; j <= i; j++) {
                log.info("==== j = {}====", j);
                // Why i - j < 3  ?
                // 1. String of length 1 is always palindrome so no need to check in boolean table
                // 2. String of length 2 is palindrome if Ci == Cj
                // which is already checked in first part so no need to check again
                // 3. String of length 3 is palindrome if Ci == Cj
                // which is already checked in first part and Ci+1 and Cj-1 is same character which is always a palindrome

                // If String length >=4
                // then check if Ci == Cj and if they are equal check if String[j+1 .. i-1] is a palindrome
                // from the boolean table
                // length of string to check = i-j+1. substring(j, i+1)
                if (s.charAt(j) == s.charAt(i) && (i - j < 3 || p[j + 1][i - 1])) {
                    // Its a palindrome as Ci == Cj and String[j+1...i-1] is a palindrome
                    p[j][i] = true;
                    log.info("setting p[{}][{}] to true , as substring {} to {}: {} is palindrome", j, i, j,i, s.substring(j,i+1));
                    // j == 0 because String from j to i is a palindrome and it starts from first character
                    // so means no cuts needed
                    // Else, I need a cut at jth location and it will be cuts encountered till j-1 plus 1 more cut
                    min = j == 0 ? 0 : Math.min(min, minCuts[j - 1] + 1);
                    log.debug("new min cuts is {}. minCuts ={} , position {}", min, minCuts, j);
                }else{
                    log.debug("comparing chars {} and {}, also checking value {} for {},{}  returned FALSE",s.charAt(j), s.charAt(i), p[j + 1][i - 1], j+1, i-1 );
                }
            }
            minCuts[i] = min;
            log.info("minCuts {}", minCuts);
        }
        log.info(" returning last cut {} means {}", minCuts, minCuts[n - 1]);
        return minCuts[n - 1];

    }
}
