package com.example.core.array;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SumInPairTest {

	
	@Test
	void test() {
		//assertEquals("2,5 4,3 1,6", solution(new int[] {7, 8, 2, 4, 5, 1, 6, 3}));
		
		assertEquals("2,5 2,5", solution(new int[] {7, 2, 2, 5, 5, 5, 6, 3}));
	}

	
	
	/**
	 * returns string in order,for all pairs that = arr[0]
	 * @param is
	 * @return
	 */
	private String solution(int[] a) {
		
		int sum = a[0];

		Map<Integer, List<Integer>> map = new HashMap<>();
		List<int[]> existingValues = new ArrayList<>();
		for (int i=1;i<a.length;i++) {
			
			int rem = sum-a[i];
			
			if (map.containsKey(rem)) {
				List<Integer> list = map.get(rem);
				int prev = list.remove(0);
				existingValues.add(new int[]{prev, i});
				if(list.size()<=0) {
					map.remove(rem);
				}
			}else {
				List<Integer> ls = map.getOrDefault(a[i], new ArrayList<>());
				ls.add(i);
				map.put(a[i], ls);
				
			}
			
		}
		
		return formatString(a, existingValues, sum);
	}

	Comparator <int[]> comparator = Comparator.comparingInt((int[] ar) -> ar[0]);


	private String formatString(int[] a, List<int []> indexes, int sum) {
		indexes.sort(comparator);
		StringBuilder sb =new StringBuilder();
		for (int[] ix: indexes) {
			sb.append(a[ix[0]]).append(",").append(a[ix[1]]).append(' ');
		}
		return sb.toString().trim();
	}
}
