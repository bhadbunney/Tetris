/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

public class LongBrick extends TetrisBrick {

   public LongBrick( int cen_col) {
        initPosition(cen_col);
        colorNum = 4;
    }
   
    @Override
    public void  initPosition(int center_col) {
        
        position = new int[][]{
                { 0,center_col-2},               
                { 0, center_col},
                { 0, center_col-1},
                { 0, center_col+1}
        };
    }
}

