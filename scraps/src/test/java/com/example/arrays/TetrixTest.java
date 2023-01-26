package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TetrixTest {
	
	@Test
	void tetrix(){
		int[][] field = new int[][]{ {0,0,0}, 
									 {0,0,0}, 
									 {0,0,0}, 
									 {1,0,0}, 
									 {1,1,0}};
		int[][] figure =
						{{0,0,1}, 
						 {0,1,1}, 
						 {0,0,1}};
		
		assertEquals(0,  solution(field, figure));
		
		field = new int[][] {{0, 0, 0, 0, 0},
				              {0, 0, 0, 0, 0},
				              {0, 0, 0, 0, 0},
				              {1, 1, 0, 1, 0},
				              {1, 0, 1, 0, 1}};

	    figure = new int[][]{{1, 1, 1},
				              {1, 0, 1},
				              {1, 0, 1}};
	    assertEquals(2,  solution(field, figure));
		
	    field =  new int[][]{ //{0, 0, 0, 0},
				              {0, 0, 0, 0},
				              {0, 0, 0, 0},
				              {1, 0, 0, 1},
				              {1, 1, 0, 1}};

	    figure = new int[][] {{1, 1, 0},
				              {1, 0, 0},
				              {1, 0, 0}};
		
	    assertEquals(-1,  solution(field, figure));
	}
	
	
	int solution(int[][] field, int[][] figure) {

	   
	    
	    /*
	    sliding from 0 to m-fig.length
	    
	    */
	    for (int c =0; c<= field[0].length -figure[0].length; c++){
	        
	        int[][] finalPos = computeFinalPosition(field, figure, c);
	        if (lines(finalPos) > 0){
	        	return c;
	        };
	        
	        
	    }
	    return -1;

	}

	private int lines (int[][] array){
	    int lines = 0;
	    for(int row=0; row< array.length; row++){
	        int ones = 0;
	        for (int c=0;c<array[row].length; c++){
	            if (array[row][c] == 1)
	                ones++;
	        }
	        if (ones == array[row].length){
	            lines++;
	        }
	    }
	    //log.info("{} has {} lines",array, lines);
	    return lines;
	}

	private int[][] computeFinalPosition(int[][] field, int[][] fig, int startColumn){
	    
		int[][]  lowered = copy(field);
		int[][]  array = null;
	    
		boolean stuck = false;
		
		// while not stuck, lower the figure
		for(int row=0; row<= field.length-fig.length &&!stuck; row++){
			array = copy(field);
			
			for (int r = 0; r < fig.length && !stuck; r++) {
				for (int c=0; c<fig[0].length && !stuck; c++){
	           
	            	if (!canMix(field[row+r][startColumn+c], fig[r][c])){
	            		stuck = true;
	            	}else {
	            		
	            		array[row+r][startColumn+c] = mix(field[row+r][startColumn+c], fig[r][c]);
	            		//log.info("{} array" , array);
	            		
	            		//log.info("{} array row after mixing {} and {}" , array[row+r], field[row+r], fig[r]);
	            	}
	            	
	            }
	            
	          
	        }
			if (!stuck) {
			   lowered = array;
			}
		
	    }
		//log.info("{} and figure {} lowered {}", field, fig, lowered);
		return lowered;
	    
	}
	
	private int[][] copy (int[][] a) {
		if (a==null) return null;
		int[][] c = new int[a.length][];
		for (int i=0; i< a.length ;i++) {
			c[i]=a[i].clone();
		}
		return c;
	}

	boolean canMix (int i, int j){
		return i!=j || i!=1;
	}
	
	int mix(int i, int j) {
		if (canMix(i,j)) {
			return i == 1 ? i : j;
		}else {
			return -1;
		}
	}
}
