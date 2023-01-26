package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTest {
	
	@Test
	void simpleTest() {
		
		
		int i=10;
		int length = 20;
		
		String s = Integer.toString(i, 2);
		//pad
		StringBuilder sb = new StringBuilder("");
		for (int j=0; j<length-s.length(); j++) {
			sb.append('0');
		}
		sb.append(s);
		assertEquals(sb.length(), length);
		s=sb.toString();
		
		log.info("{}", s);
	}
	
	

}
