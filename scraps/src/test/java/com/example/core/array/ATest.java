package com.example.core.array;

public class ATest {
	public static void main(String[] args) {

		int[][] matrix = {

				{ 1, 2, 3, 4, 5, 6 },

				{ 1, 2, 3, 4, 5, 6 },

				{ 1, 2, 3, 4, 5, 6 },

				{ 1, 2, 3, 4, 5, 6 },

				{ 1, 2, 3, 4, 5, 6 },

				{ 1, 2, 3, 4, 5, 6 }

		};

		int length = 3;

		int breath = 3;

		int max = solve(matrix, length, breath);

		System.out.println(max);

	}

	public static int solve(int[][] matrix, int length, int breath) {

		int maxSum = 0;

		for (int x = 0; x < matrix.length; x++) {

			for (int y = 0; y < matrix[x].length; y++) {

				int sum = 0;

				boolean canBeRectangle = true;

				for (int lengthMoves = 0; lengthMoves < length && canBeRectangle; lengthMoves++) {

//-- Both increase by one each time.

					int columnNoOfStartingPointOfDiagonal = x + lengthMoves;

					int rowNoOfStartingPointOfDiagonal = y + lengthMoves;

					for (int breathMoves = 0; breathMoves < breath && canBeRectangle; breathMoves++) {

						if (columnNoOfStartingPointOfDiagonal - breathMoves >= 0

								&& columnNoOfStartingPointOfDiagonal - breathMoves < matrix[0].length

								&& rowNoOfStartingPointOfDiagonal + breathMoves < matrix.length) {

							sum += matrix[rowNoOfStartingPointOfDiagonal
									+ breathMoves][columnNoOfStartingPointOfDiagonal - breathMoves];

						} else {

							canBeRectangle = false;

						}

					}

					if (lengthMoves < length - 1) {

						columnNoOfStartingPointOfDiagonal = x + lengthMoves;

						rowNoOfStartingPointOfDiagonal = y + lengthMoves + 1;

						for (int breathMoves = 0; breathMoves < breath - 1 && canBeRectangle; breathMoves++) {

							if (columnNoOfStartingPointOfDiagonal - breathMoves >= 0

									&& rowNoOfStartingPointOfDiagonal + breathMoves < matrix.length) {

								sum += matrix[rowNoOfStartingPointOfDiagonal
										+ breathMoves][columnNoOfStartingPointOfDiagonal - breathMoves];

							} else {

								canBeRectangle = false;

							}

						}

					}

				}

				if (canBeRectangle)

					maxSum = Math.max(maxSum, sum);

			}

		}

		return maxSum;

	}

}