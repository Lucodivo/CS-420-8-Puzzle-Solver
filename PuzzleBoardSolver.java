/*
Connor Haskins
Bronco ID# 010215400
April 28th, 2016
CS 420.01 w/ Dr. Fang Tang
*/

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Stack;

/*
Given a certain configuration of an 8-Puzzle written as a String
PuzzleBoardSolver can solve it and return the amount of nodes produced in the solution
It can solve the puzzle through both the (1) Misplaced Heuristic or (2) Manhattan Heuristic
*/
class PuzzleBoardSolver {
    
    // root PuzzleBoard
    private PuzzleBoard rootBoard;
    
    // configuration of root PuzzleBoard
    private String config;
    
    // number of nodes generated for last solution
    private int genNodes;
    
    // number of steps for the optimal solution
    private int steps;
    
    // constructor
    /*
    Input: String initConfig - Represents the root board values, for example the board...
                              0 1 2
                              3 4 5
                              6 7 8
                              would be represented as "012345678"
    Ouput: PuzzleBoardSolver that is capable of solving a specified configuration
            **ONLY IS CONFIGURATION IS SOLVABLE**
    */
    public PuzzleBoardSolver(String initConfig){
        // initialize and save the initial configuration in config
        this.config = new String();
        this.config = initConfig;
        // initialize generated nodes and steps to 0
        this.genNodes = 0;
        this.steps = 0;
    }
    
    /*
    call to solve the puzzle using Misplaced heuristics
    Input: void
    Output: int - Number of nodes generated to solve solution
            System.out - representing the path from root to goal
    */
    public int solveMisplaced(){
        return this.solve(1);
    }
    
    /*
    call to solve the puzzle using Manhattan distance heuristics
    Input: void
    Output: int - Number of nodes generated to solve solution
            System.out - representing the path from root to goal
    */
    public int solveManhattan(){
        return this.solve(2);
    }
    
    /*
    Input: int heuristicType - (1) to solve using Misplaced Heuristic
                               (2) to solve using Manhattan Heuristic
    Output: int - Number of nodes generated to solve solution
            System.out - representing the path from root to goal
    */
    private int solve(int heuristicType){
        // PriorityQueue is used as the frontier in our A* algorithm
        // create and initialize a new PuzzleBoardComparator
        Comparator<PuzzleBoard> pbComparator = new PuzzleBoardComparator();
        // create and initialize a new PriorityQueue, using 10 as the starting
        // size of the queue, and the pbComparator as means of comparison
        PriorityQueue<PuzzleBoard> queue = new PriorityQueue<>(10, pbComparator);
        
        // HashMap is used as the expanded nodes of our A* algorithm
        // create and initialize a HashMap linking a string to a PuzzleBoard
        // initialize initial size to 10
        HashMap<String, PuzzleBoard> hashMap = new HashMap<>(10);
        
        // create a new puzzle board using the user's configuration String
        // and the heuristic type chosen
        this.rootBoard = new PuzzleBoard(this.config, heuristicType);
        
        // add the rootBoard to our queue
        queue.add(this.rootBoard);
        
        // initialize generated nodes to 0 (in case solve gets called twice)
        this.genNodes = 0;
        
        // loop while there are still nodes on the queue
        while(queue.size() > 0){
            // remove the PuzzleBoard from the queue with the least A* value
            PuzzleBoard pb = queue.remove();
            
            // if the puzzle board is the goal...
            if(pb.isGoal()){
                // int to hold number of steps for solution
                // starts at -1, because one non-null pb is for the root node
                int steps = -1;
				// Stack to print the parent nodes in reverse order
				Stack<PuzzleBoard> stack = new Stack<>();
                while(pb != null){
                    // increase steps once per parent 
                    steps++;
                    // add pb to Stack
					stack.push(pb);
                    // get the set current board to it's parent
                    pb = pb.getParent();
                }
                
                while (stack.size() > 1) {
					stack.pop().printBoard(); 
					System.out.println("VVVVV");
                }
				stack.pop().printBoard(); 
                
                this.steps = steps;
                
                //exit the function and return number of nodes generated
                return this.genNodes;
            }
            
            // produce a puzzleBoard if the current board's empty were to move up
            PuzzleBoard tempBoard = pb.produceUp();
            // if the board isn't null and the board hasn't already been explored...
            if (tempBoard != null){
              // increment generated nodes count
              this.genNodes++;
              if(!hashMap.containsKey(tempBoard.getBoardVal())) {
                // add the board to our queue (frontier)
                queue.add(tempBoard);
              }
            }
            // produce a puzzleBoard if the current board's empty were to move down
            tempBoard = pb.produceDown();
            // if the board isn't null and the board hasn't already been explored...
            if (tempBoard != null){
              // increment generated nodes count
              this.genNodes++;
              if(!hashMap.containsKey(tempBoard.getBoardVal())) {
                // add the board to our queue (frontier)
                queue.add(tempBoard);
              }
            }
            // produce a puzzleBoard if the current board's empty were to move left
            tempBoard = pb.produceLeft();
            // if the board isn't null and the board hasn't already been explored...
            if (tempBoard != null){
              // increment generated nodes count
              this.genNodes++;
              if(!hashMap.containsKey(tempBoard.getBoardVal())) {
                // add the board to our queue (frontier)
                queue.add(tempBoard);
              }
            }
            // produce a puzzleBoard if the current board's empty were to move right
            tempBoard = pb.produceRight();
            // if the board isn't null and the board hasn't already been explored...
            if (tempBoard != null){
              // increment generated nodes count
              this.genNodes++;
              if(!hashMap.containsKey(tempBoard.getBoardVal())) {
                // add the board to our queue (frontier)
                queue.add(tempBoard);
              }
            }
            
            // put the explored PuzzleBoard into our HashMap (explored set)
            hashMap.put(pb.getBoardVal(), pb);
        }
        
        // return 0 if search failed
        return 0;
    }
    
    // getter
    public int getSteps(){
      return this.steps;
    }
}
