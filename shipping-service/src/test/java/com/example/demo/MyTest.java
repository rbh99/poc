package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class MyTest {


    @Test
    void mytest() {
        //
        //pick fifth largest element
        int[] array = {-10, 3, 9, 2, -1, 0, 7, 4, 2};

        System.out.println("array = " + Arrays.toString(array));



        Arrays.sort(array);
        System.out.println("fifth =" + array[array.length - 5]);

        Arrays.stream(array)
                .sorted()
                .skip(4)
                .findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("none"));

    }


}
