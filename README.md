# CS-420-8-Puzzle-Solver

/*
**	README
**	8-Puzzle Program
**	by Connor Haskins
**	4/28/2016
*/

CS 420 Artificial Intelligence: 8-Puzzle Solver using A* algorithm with two different heuristic functions

FOR BUILDING/RUNNING...
- in the command prompt in a working directory with EightPuzzle.java, PuzzleBoard.java, 
PuzzleBoardComparator, and PuzzleBoardSolver
- use "javac *.java" to compile
- when successful, use "java EightPuzzle" to run program

WHEN PROGRAM IS RUNNING...

- The program asks for the user to choose between two options: solve a randomly generated puzzle, or solve a puzzle that the user manually enters
- After the program outputs:
	- "Would you like to..."
        - "1 - Create a randomly generated 8-puzzle problem?"
        - "2 - Enter a specific 8-puzzle configuration?"
- The user either types in '1' or '2' (A single char, without the single quotes)
- If the user chooses 1:
	- a random board is generated and solved
	- both the solution found by A* using the Manhattan AND A* using the Misplaced hueristic will print out
	- at the end it will display the amount of nodes that the A* algorithm produced
- if the user chooses 2:
	- the program outputs:
		- "Enter an 8x8 puzzle in the format: "
		- "x x x"
		- "x x x"
		- "x x x"
	- the user should enter a 3x3 matrix. 
	- For example, the input could be:
		0 2 1
		3 5 4
		6 7 8
	- The input MUST be three separate lines, although it doesn't have to be rows, necessarily
	- The input MUST be the 9 characters of [0 - 8], inclusive
	- both the solution found by A* using the Manhattan AND A* using the Misplaced hueristic will print out
	- Each solution will end displaying the amount of nodes that the A* algorithm produced
