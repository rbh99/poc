package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinesweeperTest {
	/*
	 * 
	 * Minesweeper is a popular single-player computer game. The goal is to locate
	 * mines within a rectangular grid of cells. At the start of the game, all of
	 * the cells are concealed. On each turn, the player clicks on a blank cell to
	 * reveal its contents, leading to the following result:
	 * 
	 * If there's a mine on this cell, the player loses and the game is over;
	 * Otherwise, a number appears on the cell, representing how many mines there
	 * are within the 8 neighbouring cells (up, down, left, right, and the 4
	 * diagonal directions); If the revealed number is 0, each of the 8 neighbouring
	 * cells are automatically revealed in the same way.
	 * 
	 * demonstration
	 * 
	 * You are given a boolean matrix field representing the distribution of bombs
	 * in the rectangular field. You are also given integers x and y, representing
	 * the coordinates of the player's first clicked cell - x represents the row
	 * index, and y represents the column index, both of which are 0-based.
	 * 
	 * Your task is to return an integer matrix of the same dimensions as field,
	 * representing the resulting field after applying this click. If a cell remains
	 * concealed, the corresponding element should have a value of -1.
	 * 
	 * It is guaranteed that the clicked cell does not contain a mine.
	 * 
	 * Example
	 * 
	 * For
	 * 
	 * field = [[false, true, true], [true, false, true], [false, false, true]]
	 * 
	 * x = 1, and y = 1, the output should be
	 * 
	 * solution(field, x, y) = [[-1, -1, -1], [-1, 5, -1], [-1, -1, -1]]
	 * 
	 * example
	 * 
	 * There are 5 neighbors of the cell (1, 1) which contain a mine, so the value
	 * in (1, 1) should become 5, and the other elements of the resulting matrix
	 * should be -1 since no other cell would be expanded.
	 * 
	 * For
	 * 
	 * field = [[true, false, true, true, false], [true, false, false, false,
	 * false], [false, false, false, false, false], [true, false, false, false,
	 * false]]
	 * 
	 * x = 3, and y = 2, the output should be
	 * 
	 * solution(field, x, y) = [[-1, -1, -1, -1, -1], [-1, 3, 2, 2, 1], [-1, 2, 0,
	 * 0, 0], [-1, 1, 0, 0, 0]]
	 * 
	 * example
	 * 
	 * Since the value in the cell (3, 2) is 0, all of its neighboring cells ((2,
	 * 1), (2, 2), (2, 3), (3, 1), and (3, 3)) are also revealed. Since the value in
	 * the cell (2, 2) is also 0, its neighbouring cells (1, 1), (1, 2) and (1, 3)
	 * are revealed, and since the value in cell (2, 3) is 0, its neighbours (1, 4),
	 * (2, 4), and (3, 4) are also revealed. The cells (3, 3), (2, 4), and (3, 4)
	 * also contain the value 0, but since all of their neighbours have already been
	 * revealed, no further action is required.
	 * 
	 * Input/Output
	 * 
	 * [execution time limit] 3 seconds (java)
	 * 
	 * [input] array.array.boolean field
	 * 
	 * A rectangular matrix representing the locations of the mines in the game
	 * field.
	 * 
	 * Guaranteed constraints: 2 ≤ field.length ≤ 100, 2 ≤ field[i].length ≤ 100.
	 * 
	 * [input] integer x
	 * 
	 * The row number of the cell which is clicked (0-based).
	 * 
	 * Guaranteed constraints: 0 ≤ x < field.length.
	 * 
	 * [input] integer y
	 * 
	 * The column number of the cell which is clicked (0-based).
	 * 
	 * Guaranteed constraints: 0 ≤ y < field[0].length.
	 * 
	 * [output] array.array.integer
	 * 
	 * The expanded matrix after the click.
	 * 
	 */
	@Test
	void test() {
		boolean[][] field = new boolean[][] { { false, true, true }, { true, false, true }, { false, false, true } };
		int[][] sol = new int[][] { { -1, -1, -1 }, { -1, 5, -1 }, { -1, -1, -1 }

		};
		// assertTrue(Arrays.equals(sol, solution(field,1,1)));

		field = new boolean[][] { { true, false, true, true, false }, { true, false, false, false, false },
				{ false, false, false, false, false }, { true, false, false, false, false } };

		sol = new int[][] { { -1, -1, -1, -1, -1 }, { -1, 3, 2, 2, 1 }, { -1, 2, 0, 0, 0 }, { -1, 1, 0, 0, 0 } };
		int[][] tested = solution(field, 3, 2);
		log.info("returned solution {}", tested);
		log.info("expected solution {}", sol);
		assertTrue(Arrays.deepEquals(sol, tested));

	}

	int[][] solution(boolean[][] field, int x, int y) {

		int[][] sol = new int[field.length][field[0].length];
		for (int i = 0; i < field.length; i++) {
			Arrays.fill(sol[i], -1);
		}

		populateNeighbours(sol, field, x, y);
		log.info("populated {} ", sol);

		int count = 0;
		while (!allZeroNeighboursRevealed(sol, field) && count < 10) {
			revealZeroNeighbours(sol, field);
			count++;
		}
		;
		return sol;
	}

//returns true if all zero neighbours have value >=0 neighbours
	boolean allZeroNeighboursRevealed(int[][] sol, boolean[][] field) {

		log.info("allZeroNeighboursRevealed ");
		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol[0].length; j++) {
				if (sol[i][j] == 0 && unrevieled(sol, i, j)) {
					log.info("allZeroNeighboursRevealed false");
					return false;
				}
			}
		}
		log.info("allZeroNeighboursRevealed true");
		return true;
	}

//reveals all neighbors of a zero cell
	void revealZeroNeighbours(int[][] sol, boolean[][] field) {
		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol[0].length; j++) {
				if (sol[i][j] == 0) {

					for (int r = i - 1; r <= i + 1; r++) {
						for (int c = j - 1; c <= j + 1; c++) {
							if (r >= 0 && r < sol.length && c >= 0 && c < sol[0].length) {
								if (sol[r][c] == -1) {
									populateNeighbours(sol, field, r, c);
								}
							}
						}
					}

					populateNeighbours(sol, field, i, j);
				}
			}
		}
	}

//returns false if there are -1 value neighbors
	boolean unrevieled(int[][] sol, int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < sol.length && j >= 0 && j < sol[0].length) {
					if (sol[i][j] == -1) {
						log.info("cell [{}][{}] is unrevieled. return true ", i, j);
						return true;
					}
				}
			}
		}
		return false;
	}

	void populateNeighbours(int[][] sol, boolean[][] field, int x, int y) {

		int count = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i >= 0 && i < field.length && j >= 0 && j < field[0].length) {
					if (field[i][j]) {
						count++;
					}
				}

			}
		}
		sol[x][y] = count;
		log.info("populated neighbours for [{}][{}]  {}", x, y, sol);

	}
}