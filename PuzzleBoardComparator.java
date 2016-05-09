/*
Connor Haskins
Bronco ID# 010215400
April 28th, 2016
CS 420.01 w/ Dr. Fang Tang
*/

import java.util.Comparator;

/*
comparator class used for our PriorityQueue to sort our PuzzleBoards
PuzzleBoards are sorted by their A* algorithm value
this value is calculated by f(n) = g(n) + h(n)
where g(n) is the depth of the node, 
and h(n) is one of two heuristic functions known to the node itself
*/
class PuzzleBoardComparator implements Comparator<PuzzleBoard> {
    @Override
    public int compare(PuzzleBoard p1, PuzzleBoard p2){
        // if the first board has a lesser A* value, 
        // p1 is less than p2, return -1 to indicate
        if (p1.getAStarVal() < p2.getAStarVal())
        {
            return -1;
        }
        // if the first board has a greater A* value, 
        // p1 is greater than p2, return +1 to indicate
        if (p1.getAStarVal() > p2.getAStarVal())
        {
            return 1;
        }
        // else the A* values are equal
        // p1 is equal to p2, so return 0 to indicate
        return 0;
    }
}
