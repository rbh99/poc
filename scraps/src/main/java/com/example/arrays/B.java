package com.example.arrays;

import java.util.Comparator;

public class B {
	
	private static Comparator<String> cs = ((s1, s2) -> s1.compareToIgnoreCase(s2));
	
	

    public static void main(String[] args) {

        String str = "123";
        System.out.println(reverse(str));
        
       
        
    }

    public static String reverse(String in) {
        
    	return new StringBuilder(in).reverse().toString();
    }

}
