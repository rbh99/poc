package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTest {
	
	@Test
	void test() {
		assertEquals("rab", solution("(bar)"));
	}
	
	String solution(String inputString) {

	    int start = inputString.lastIndexOf("(");
	    int end = inputString.indexOf(")", start);
	    
	    while(start < end){
	        
	       inputString = inputString.substring(0,start)
	       +reverse(inputString.substring(start+1, end))
	       +inputString.substring(end+1); 
	        
	         start = inputString.lastIndexOf("(");
	         end = inputString.indexOf(")", start);
	    }
	    
	    return inputString;
	}

	String reverse (String s){
	    return new StringBuilder(s).reverse().toString();
	}

}
