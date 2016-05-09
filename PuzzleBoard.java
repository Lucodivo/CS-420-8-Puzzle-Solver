/*
Connor Haskins
Bronco ID# 010215400
April 28th, 2016
CS 420.01 w/ Dr. Fang Tang
*/

import java.util.Random;

/*
PuzzleBoard represents an 8x8 puzzle configuration and allows for several operations
It can calculate it's own A* value based on the hueristic assigned:
    1 - for heuristic that determines amount of misplaced tiles
    2 - for heuristic that determines the Manhattan distance of each tile from goal
It can also produce new puzzle boards that are a result of 'moving' the empty tile
in any of the four directions.
*/
class PuzzleBoard {

    // the positions of all the squares on the board
    private StringBuffer boardValues;
    
    // parent of the PuzzleBoard
    private PuzzleBoard parent;
    
    // hueristic used
    private int heuristicType;
    
    // depth of node
    private int depth;
    
    // A* val = g(n) + h(n)
    private int aStarVal;
    
    // empty index
    private int emptyIndex;

    /* private constructor
    Input:  String boardVal - Represents the board values, for example the board...
                              0 1 2
                              3 4 5
                              6 7 8
                              would be represented as "012345678"
            int h - represents the heuristic function that will be used
                    h == 1 should be true for misplaced heuristic
                    h == 2 should be true for manhattan distance heuristic
    Output: a new PuzzleBoard with the boardVal configuration, set to use the
            specified heuristic, with depth 0 and parent == null
    */ 
    public PuzzleBoard(String boardVal, int h) {
        // call private constructor with the depth of 0 and parent as null
        this(boardVal, 0, null, h);
    }
    
    
    /* private constructor
    Input:  String boardVal - *SAME AS PRIVATE*
            int d - The depth of the the node from the root node
            PuzzleBoard p - the parent node of the node being created
            int h - *SAME AS PRIVATE*
    Output: a new PuzzleBoard with the boardVal configuration, set to use the
            specified heuristic, with depth d and parent == p
    */
    private PuzzleBoard(String boardVal, int d, PuzzleBoard p, int h){
        // initialize boardValues and fill the board with the values passed
        this.boardValues = new StringBuffer(boardVal);
        
        // determine where the empty slot is
        for (int i = 0; i < 9; i++) {
            // check each slot for the value of '0'
            if (this.boardValues.charAt(i) == '0') {
                // when '0' is found, mark the index and break the for loop
                this.emptyIndex = i;
                break;
            }
        }
        
        this.heuristicType = h; // set heuristicType to argument passed
        this.depth = d;         // set depth to argument passed 
        this.parent = p;        // set parent to argument passed
        this.setAStar();        // initialize A* value
    }
    
    /*
    a function that (re-)calculates the A* value of the PuzzleBoard
    Input: void
    Output: void
    */
    private void setAStar(){
        // the the heuristic type is 1, use the heuristicMisplaced() function to calculate A*
        if(this.heuristicType == 1){
            // A* = g(n) + h(n)
            this.aStarVal = this.depth + this.heuristicsMisplaced();
        } 
        // else, used the heuristicsManhattan() function to calculate A*
        else {
            // A* = g(n) + h(n)
            this.aStarVal = this.depth + this.heuristicsManhattan();
        }
    }

    /*
    (1) Heurstics function that counts the number of misplaced tiles
    Input: void
    Output: int representing the number of misplaced tiles
     */
    private int heuristicsMisplaced() {
        // int to hold number of misplaced values
        int numMisplaced = 0;
        // loop through every tile and discover if it is misplaced
        for (int i = 0; i < 9; i++) {
            // compare each tile with corresponding value int [0-8] have equivalent
            // chars at values [48-56]
            if (this.boardValues.charAt(i) != (char) (48 + i)) {
                // incremend numMisplaced whenever a match is found
                numMisplaced++;
            }
        }
        // return the number of misplaced tiles
        return numMisplaced;
    }
    
    /*
    (2) Heuristics function that returns the sum of distances of the tiles from their goal positions
    Input: void
    Output: int representing the number of the manhattan distance of the node to goal
     */
    private int heuristicsManhattan() {
        // int to hold manhattan distance sum
        int manhattanDistance = 0;
        // iterate through each square
        for (int currentIndex = 0; currentIndex < 9; currentIndex++) {
            // calculate the current index's row
            int indexRow = currentIndex / 3;
            // calculate the current index's column
            int indexCol = currentIndex % 3;
            // get the current value of tile as an int
            int currentValue = (int) this.boardValues.charAt(currentIndex) - 48;
            // calculate the row the value SHOULD be in
            int valueRow = currentValue / 3;
            // calculate the col the value SHOULD be in
            int valueCol = currentValue % 3;
            // manhattan distance = abs(actual row - desired row) + abs(actual col - valuecol)
            // add new manhattan distance of tile to manhattanDistance sum
            manhattanDistance += Math.abs(indexRow - valueRow) + Math.abs(indexCol - valueCol);
        }
        return manhattanDistance;
    }

    /*
    tests whether the puzzle it at the goal state by using heuristicsMisplaced
    and ensuring there are no misplaced tiles
    Input: void
    Ouput: boolean representing if the current PuzzleBoard is at the goal state
     */
    public boolean isGoal() {
        return this.boardValues.toString().equals("012345678");
    }
    

    /*
    calculates inversions of the puzzle
    if i < j and puzzle[i] > puzzle[j], there exists an inversion
    only a problems with an even amount of inversions are valid
    Input: void
    Output: boolean representing if the PuzzleBoard is solvable
     */
    public boolean isSolvable() {
        // int to hold number of inverted tiles
        int numInverted = 0;
        // iterate through every tile i
        for (int i = 0; i < 8; i++) {
            // '0' is treated as empty, it is not actually a 0 and will not be used in inversions
            if (this.boardValues.charAt(i) != '0') {
                // iterate through every tile in front of the i tile
                for (int j = i + 1; j < 9; j++) {
                    // if value in ith position is greater than value in jth position...
                    // '0' is treated as empty, it is not actually a 0 and will not be used in inversions
                    if (this.boardValues.charAt(j) != '0' && (int) this.boardValues.charAt(i) > (int) this.boardValues.charAt(j)) {
                        // increment number of inverted tiles
                        numInverted++;
                    }
                }
            }
        }

        // if inverted tiles are even, the problem is solvable
        if ((numInverted % 2) == 0) {
            return true;
        }
        // if inverted tiles are not even, the problem is unsolvable
        else {
            return false;
        }
    }

    /*
    takes a 3x3 puzzle matrix represented as a char array of 8 characters
    and performs n amount of random LEGAL 8-puzzle moves
     */
    public void scrambleBoard(int n) {
        // create a new Random object
        Random rand = new Random();
        // int to hold our new move
        int newMove;
        // move n amount of times
        for (int i = 0; i < n; i++) {
            // generate a new random move
            newMove = rand.nextInt(4);
            /*
                up           0
    	    left  right    1   2
    	        down         3
             */
            switch (newMove) {
                case 0:
                    // if empty isn't on upper row, move empty up
                    if (this.emptyIndex > 2) {
                        // to move "up" we must swap the empty tile with the character 3 tiles before the empty
                        this.boardValues.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex - 3));
                        this.emptyIndex -= 3;
                        this.boardValues.setCharAt(this.emptyIndex, '0');
                    }
                    break;
                case 1:
                    // if empty isn't on left column, move empty left
                    if (this.emptyIndex % 3 != 0) {
                        // to move "left" we must swap the empty tile with the character 1 tiles before the empty
                        this.boardValues.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex - 1));
                        this.emptyIndex--;
                        this.boardValues.setCharAt(this.emptyIndex, '0');
                    }
                    break;
                case 2:
                    // if empty isn't on right column, move empty right
                    if (this.emptyIndex % 3 != 2) {
                        // to move "right" we must swap the empty tile with the character 1 tiles after the empty
                        this.boardValues.setCharAt(this.emptyIndex, this.boardValues.charAt(emptyIndex + 1));
                        this.emptyIndex++;
                        this.boardValues.setCharAt(this.emptyIndex, '0');
                    }
                    break;
                case 3:
                    // if empty isn't on lower row, move empty down
                    if (this.emptyIndex < 6) {
                        // to move "down" we must swap the empty tile with the character 3 tiles after the empty
                        this.boardValues.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex + 3));
                        this.emptyIndex += 3;
                        this.boardValues.setCharAt(this.emptyIndex, '0');
                    }
                    break;
            }
        }
    }
    
    // setters
    /*
    sets board to desired value
    */
    public void setBoard(String newVals){
        // assign boardValues to new StringBuffer using new values
        this.boardValues = new StringBuffer(newVals);
        // recalculate the A* value
        this.setAStar();
    }
    
    // getters
    /*
    returns A* value 
    this is 
    */
    public int getAStarVal(){
        return this.aStarVal;
    }
    
    /*
    returns parent node
    */
    public PuzzleBoard getParent(){
        return this.parent;
    }
    
    /*
    return board values as a string
    */
    public String getBoardVal(){
        return this.boardValues.toString();
    }
    
    /*
    produces a PuzzleBoard if the blank space were to move up
    Input: void
    Output: returns Puzzboard if move is legal
            returns null if move is not legal
    */
    public PuzzleBoard produceUp(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on top row, produce up movement
        if (this.emptyIndex > 2) {
            // to move "up" we must swap the empty tile with the character 3 tiles before the empty
            newValue.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex - 3));
            newValue.setCharAt(this.emptyIndex - 3, '0');
            // return a new PuzzleBoard as a child produced from a upward movement
            return new PuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }
    
    /*
    produces a PuzzleBoard if the blank space were to move down
    Input: void
    Output: returns Puzzboard if move is legal
            returns null if move is not legal
    */
    public PuzzleBoard produceDown(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on the bottom row, produce down movement
        if (this.emptyIndex < 6) {
            // to move "down" we must swap the empty tile with the character 3 tiles after the empty
            newValue.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex + 3));
            newValue.setCharAt(this.emptyIndex + 3, '0');
            // return a new PuzzleBoard as a child produced from a downward movement
            return new PuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }
    
    /*
    produces a PuzzleBoard if the blank space were to move left
    Input: void
    Output: returns Puzzboard if move is legal
            returns null if move is not legal
    */
    public PuzzleBoard produceLeft(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on left column, produce left movement
        if (this.emptyIndex % 3 != 0) {
            // to move "left" we must swap the empty tile with the character 1 tiles before the empty
            newValue.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex - 1));
            newValue.setCharAt(this.emptyIndex - 1, '0');
            // return a new PuzzleBoard as a child produced from a leftward movement
            return new PuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }
    
    /*
    produces a PuzzleBoard if the blank space were to move right
    Input: void
    Output: returns Puzzboard if move is legal
            returns null if move is not legal
    */
    public PuzzleBoard produceRight(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on right column, produce right movement
        if (this.emptyIndex % 3 != 2) {
            // to move "right" we must swap the empty tile with the character 1 tiles after the empty
            newValue.setCharAt(this.emptyIndex, this.boardValues.charAt(this.emptyIndex + 1));
            newValue.setCharAt(this.emptyIndex + 1, '0');
            // return a new PuzzleBoard as a child produced from a rightward movement
            return new PuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }

    /*
    prints the values of the board
    used to test and demo the 8-puzzle project
     */
    public void printBoard() {
        System.out.println(this.boardValues.charAt(0) + " " + this.boardValues.charAt(1) + " " + this.boardValues.charAt(2));
        System.out.println(this.boardValues.charAt(3) + " " + this.boardValues.charAt(4) + " " + this.boardValues.charAt(5));
        System.out.println(this.boardValues.charAt(6) + " " + this.boardValues.charAt(7) + " " + this.boardValues.charAt(8));
    }
}
