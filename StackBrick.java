/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

public class StackBrick extends TetrisBrick{
    
    public StackBrick(int cen_col) {
        initPosition( cen_col);
        colorNum = 5;
    }
    
    @Override
    public void initPosition( int center_col) {
        
        position = new int[][]{
                    { 0,center_col},
                    { 1, center_col},
                    { 1, center_col-1},
                    { 1, center_col+1}
        };
    }
 
 }