package com.example.core.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
//@ContextConfiguration(initializers =   ConfigDataApplicationContextInitializer.class)
public class CountTeamsTest {

    //@Value("${logging.pattern.console}")
    //private String s;
    @Test
    void test1(){

        //System.out.println(s);
        int minPlayers = 3;
        int minLevel = 4;
        int maxLevel = 10;
        List<Integer> skills = new ArrayList<>(Arrays.asList(2, 4, 5, 6, 10, 16));

        log.debug("skills {}", skills);
        int result = countTeams(skills, minPlayers, minLevel, maxLevel);
        assertEquals(5, result);
    }




    private int countTeams(List<Integer> skills, int minPlayers, int minLevel, int maxLevel) {

        if (maxLevel < minLevel) {
            throw new IllegalArgumentException("maxLevel smaller than minLevel");
        }

        if (skills.isEmpty() || minPlayers > skills.size()) {
            return 0;
        }

        long size =  skills.stream().filter(skill -> skill >= minLevel && skill<= maxLevel).count();
        log.debug("{} skills are in the range", size);

        List<int[]> selection = generateCombinationInLexicographicOrder((int) size, minPlayers);
        selection.stream().map(Arrays::toString).forEach(System.out::println);
        log.debug("combinations size {} with {} players ", selection.size(), minPlayers);

        int comb = combinations((int) size, minPlayers);
        log.debug("combinations size {} with {} players ", comb, minPlayers);

        int sum = 0;
        for (int i = minPlayers; i <= size; i++) {
            sum = sum + combinations((int)size, i);
        }
        log.debug("all combinations with any numbers of valid players {}", sum);
        return sum;

    }

    /**
     * In the iterative approach, we start with an initial combination. Then, we
     * keep generating the next combination from the current one until we have
     * generated all combinations.
     *
     * Let's generate the combinations in lexicographic order. We start with the
     * lowest lexicographic combination.
     *
     * In order to get the next combination from the current one, we find the
     * rightmost location in the current combination that can be incremented. Then,
     * we increment the location and generate the lowest possible lexicographic
     * combination to the right of that location.
     * Combinations of n taken by r
     */
    private List<int[]> generateCombinationInLexicographicOrder(int n, int r) {

        List<int[]> combinations = new ArrayList<>();

        int[] combination = new int[r];
        // initialize with the lowest lexicographic combination 0...r
        for (var i = 0; i < r; i++) {
            combination[i] = i;
        }

        //generate the rest
        while (combination[r - 1] < n) {
            combinations.add(combination.clone());
            //System.out.println(Arrays.toString(combination));

            // generate next combination in lexicographic order
            int t = r - 1; //what index to increase, until it reaches last index
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;

            //recalculate right indexes, greater than t
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    /**
     * number of combinations of n integers taken by r
     */
     int combinations(int n, int r) {

        long under = 1;
        for (int i=1; i <= r; i++) {
            under = under * i;
        }
        long up = 1;
        for (int i=0; i < r; i++) {
            up = up*(n - i);
        }
        return (int) (up/under);

    }

    /**
     * another method to generate subsets
     */
    private void subarraysBitwise(int[] arr) {

        int n = arr.length;
        // Run a loop for printing all 2^n
        // subsets one by one
        for (int i = 0; i < (1<<n); i++)
        {
            System.out.print("{ ");

            // Print current subset
            for (int j = 0; j < n; j++)

                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0)
                    System.out.print(arr[j] + " ");

            System.out.println("}");
        }

    }
}
