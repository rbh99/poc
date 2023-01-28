package com.example.core.api;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.*;

public class BigDecimalTest {

    @Test
    void main(){
        String input = "9\n" +
                "-100\n" +
                "50\n" +
                "0\n" +
                "56.6\n" +
                "90\n" +
                "0.12\n" +
                ".12\n" +
                "02.34\n" +
                "000.000";

        //Input
        Scanner sc= new Scanner(new ByteArrayInputStream(input.getBytes()));
        int n=sc.nextInt();
        String []s=new String[n+2];
        for(int i=0;i<n;i++){
            s[i]=sc.next();
        }
        sc.close();

        //Write your code here
        TreeMap<BigDecimal, List<String>> map = new TreeMap<>(Comparator.reverseOrder());
        for (String st: s){
            System.out.println(" string is "+st);
            if (st==null)
                continue;
            BigDecimal bd = new BigDecimal(st);
            map.putIfAbsent(bd, new ArrayList<>());
            map.get(bd).add(st);
        }

        s = map.entrySet().stream()
                .flatMap(e-> e.getValue().stream())
                .toArray(String[]::new);


        //Output
        for(int i=0;i<n;i++)
        {
            System.out.println(s[i]);
        }
    }




}
