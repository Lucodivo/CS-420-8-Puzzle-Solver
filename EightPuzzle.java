/*
Connor Haskins
Bronco ID# 010215400
April 28th, 2016
CS 420.01 w/ Dr. Fang Tang
*/

import java.util.Scanner;
import java.io.*;
/*
eightPuzzle serves as the main class of the 8-Puzzle program
*/
public class EightPuzzle {

    // constructor
    public EightPuzzle() {
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        
        System.out.println("Would you like to...");
        System.out.println("1 - Create a randomly generated 8-puzzle problem?");
        System.out.println("2 - Enter a specific 8-puzzle configuration?");
        
        int userChoice = reader.nextInt();
        reader.nextLine();
        
        PuzzleBoard testBoard = new PuzzleBoard("012345678", 2);
        while(userChoice != 1 && userChoice != 2){
            System.out.println("Sorry, I don't recognize that input. 1 or 2.");
            userChoice = reader.nextInt();
        }
        if(userChoice == 1){
            testBoard.scrambleBoard(150);
        } else if (userChoice == 2){
            boolean puzzleValidity = false;
            while (!puzzleValidity) {
                System.out.println("Enter an 8x8 puzzle in the format: ");
                System.out.println("x x x");
                System.out.println("x x x");
                System.out.println("x x x");

                String userInputVals;
        
                userInputVals = reader.nextLine();
                userInputVals += reader.nextLine();
                userInputVals += reader.nextLine();

                userInputVals = userInputVals.replaceAll("\\s", "");
                
                testBoard.setBoard(userInputVals);
                puzzleValidity = testBoard.isSolvable();
                if(!puzzleValidity){
                    System.out.println("Invalid Puzzle. Unsolvable.");
                }
            }
        }
		System.out.println();
        System.out.println("Board start state: ");
        testBoard.printBoard();
		System.out.println();
        
        PuzzleBoardSolver boardSolver = new PuzzleBoardSolver(testBoard.getBoardVal());
        System.out.println("===================");
        System.out.println("Misplaced Heuristic");
        System.out.println("===================");
		System.out.println();
        System.out.println("\nNodes generated: " + boardSolver.solveMisplaced());
        System.out.println("Depth of problem: " + boardSolver.getSteps());
		System.out.println();
        System.out.println("===================");
        System.out.println("Manhattan Heuristic");
        System.out.println("===================");
		System.out.println();
        System.out.println("\nNodes generated: " + boardSolver.solveManhattan());
        System.out.println("Depth of problem: " + boardSolver.getSteps());
		
		// used to test, can use values [2-20] for varying depths
        /*
        EightPuzzle mainPuzzle = new EightPuzzle();
        mainPuzzle.test200(20);
		*/
    }
	
	// function simply for testing time, memory usage, and step costs
	/*
    public void test200(int depth){
        String fileName = "Depth " + depth + ".txt";
        String line = null;
        
        try {
            FileReader fileReader = new FileReader(fileName);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            int lineCount = 0;
            double totalManhattanNodes = 0;
            double totalManhattanSteps = 0;
			long totalManhattanMilli = 0;
            double totalMisplacedNodes = 0;
            double totalMisplacedSteps = 0;
			long totalMisplacedMilli = 0;
            long delta = 0;
            PuzzleBoardSolver boardSolver;
            while((line = bufferedReader.readLine()) != null){
                lineCount++;
                boardSolver = new PuzzleBoardSolver(line);
                delta = System.currentTimeMillis();
                totalManhattanNodes += boardSolver.solveManhattan();
				totalManhattanMilli += System.currentTimeMillis() - delta;
                totalManhattanSteps += boardSolver.getSteps();
                delta = System.currentTimeMillis();
                totalMisplacedNodes += boardSolver.solveMisplaced();
				totalMisplacedMilli += System.currentTimeMillis() - delta;
                totalMisplacedSteps += boardSolver.getSteps();
            }
            
            System.out.println("Average nodes produced for Manhattan at depth " + depth + ": " + (totalManhattanNodes / lineCount));
            System.out.println("Average steps for Manhattan at depth " + depth + ": " + (totalManhattanSteps / lineCount));
            System.out.println("Average time(ms) for Manhattan at depth " + depth + ": " + (totalManhattanMilli / lineCount));
            System.out.println("Average nodes produced for Misplaced at depth " + depth + ": " + (totalMisplacedNodes / lineCount));
            System.out.println("Average steps for Misplaced at depth " + depth + ": " + (totalMisplacedSteps / lineCount));
            System.out.println("Average time(ms) for Misplaced at depth " + depth + ": " + (totalMisplacedMilli / lineCount));
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
	*/
}
