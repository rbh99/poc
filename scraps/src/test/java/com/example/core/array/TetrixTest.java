package com.example.core.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


	/**
	 * returns the column where to slide down
	 * in order to have a whole line filled with 1s in merged field and figure
	 */
	int solution(int[][] field, int[][] figure) {

	    /*
	      sliding starting columns from 0 to field[0].length - fig[0].length
	    
	    */
		for (int c = 0; c <= field[0].length - figure[0].length; c++) {
			int[][] finalPos = computeFinalPosition(field, figure, c);
			if (lines(finalPos) > 0) {
				return c;
			}
		}
		return -1;
	}

	/**
	 * returns the number of lines that are filled with 1's
	 */
	int lines(int[][] array) {
		int lines = 0;
		for (int[] ints : array) {
			int ones = 0;
			for (int anInt : ints) {
				if (anInt == 1)
					ones++;
				else break;
			}
			if (ones == ints.length) {
				lines++;
			}
		}
		//log.info("{} has {} lines",array, lines);
		return lines;
	}

	/**
	 * return final position with fig lowered until not stuck on the field
	 * lowering starting at column startColumn
	 */
	int[][] computeFinalPosition(int[][] field, int[][] fig, int startColumn) {

		int[][] lowered = copy(field);
		int[][] array;

		boolean stuck = false;

		// while not stuck, lower the figure
		for (int row = field.length - fig.length; row >= 0 && !stuck; row--) {
			//lower one row
			array = copy(field);

			array = mergeArrays (field, fig, row, startColumn).orElse(null);
			if (array != null)
				return array;

			//check if arrays can merge
			/*for (int r = 0; r < fig.length && !stuck; r++) {
				for (int c = 0; c < fig[0].length && !stuck; c++) {

					if (!canMerge(field[row + r][startColumn + c], fig[r][c])) {
						stuck = true;
					} else {
						array[row + r][startColumn + c] = merge(field[row + r][startColumn + c], fig[r][c]);
						//log.info("{} array" , array);

						//log.info("{} array row after mixing {} and {}" , array[row+r], field[row+r], fig[r]);
					}
				}
			}
			if (!stuck) {
				lowered = array;
			}*/

		}
		//log.info("{} and figure {} lowered {}", field, fig, lowered);
		return lowered;

	}



	Optional<int[][]> mergeArrays(int[][] field, int[][] fig, int startRow, int startColumn) {
		//todo assumptions
		boolean stuck = false;
		int[][] array = copy(field);
		for (int r = 0; r < fig.length && !stuck; r++) {
			for (int c = 0; c < fig[0].length && !stuck; c++) {
				if (!canMerge(field[startRow + r][startColumn + c], fig[r][c])) {
					stuck = true;
				} else {
					array[startRow + r][startColumn + c] = merge(field[startRow + r][startColumn + c], fig[r][c]);
				}
			}
		}
		return stuck ? Optional.empty() : Optional.of(array);
	}


	/**
	 * makes a clone of the array
	 */
	private int[][] copy (int[][] a) {
		if (a==null) return null;
		return Arrays.stream(a).map(int[]::clone).toArray(int[][]::new);

		/*int[][] c = new int[a.length][];
		for (int i=0; i< a.length ;i++) {
			c[i]=a[i].clone();
		}
		return c;*/
	}

	/**
	 * returns true if i and j are different or one is 0
	 */
	boolean canMerge(int i, int j){
		return i!=j || i!=1;
	}

	/**
	 * return 1 if only one is 1, 0 if both 0, or -1 if both 1
	 * merge the 2 values if it can merge otherwise return -1
	 */
	int merge(int i, int j) {
		if (canMerge(i,j)) {
			return i == 1 ? i : j;
		}else {
			return -1;
		}
	}
}
