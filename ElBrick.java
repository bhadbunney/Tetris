/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

public class ElBrick extends TetrisBrick{
    
    public ElBrick(int cen_col) {
        initPosition(cen_col);
        colorNum = 7;
    }
    
    @Override
    public void initPosition( int center_col){
        
        position = new int[][]{
                { 2,center_col+1},
                { 1, center_col},
                { 2, center_col},
                { 0, center_col}
        };
    }
 
 }