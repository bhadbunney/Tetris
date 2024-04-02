/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

public class SquareBrick extends TetrisBrick{
    
   public SquareBrick(int cen_col) {
        initPosition(cen_col);
        colorNum = 6;
    }
   
   @Override
    public void initPosition(int center_col) {
        
        position = new int[][]{
                { 0,center_col+1},
                { 0, center_col},
                { 1, center_col+1},
                { 1, center_col}
        };
    }
 
 }