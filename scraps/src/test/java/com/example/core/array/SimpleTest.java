package com.example.core.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SimpleTest {
	
	@Test
	void simpleTest() {
		
		
		int i=10;
		int length = 20;
		
		String s = Integer.toString(i, 2);
		//pad
		StringBuilder sb = new StringBuilder();
		sb.append("0".repeat(Math.max(0, length - s.length())));
		sb.append(s);
		assertEquals(sb.length(), length);
		s=sb.toString();
		
		log.info("{}", s);
	}
	
	

}
