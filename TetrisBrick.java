/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

import java.awt.Color;

public abstract class TetrisBrick {
    
    protected int numSegments = 4;
    protected int[][] position;
    protected Color color;
    protected int colorNum;

    public TetrisBrick() {
   
    }
    
    public String toString() {
        
        String brick = color.toString() + colorNum;
  
        for (int parts = 0; parts < numSegments; parts++) {
            brick += String.valueOf(position[parts][0]);
            brick += String.valueOf(position[parts][1]) +"\n";
        }
        return brick;
        
    }
    
    public void moveDown(){
        for(int segment = 0; segment < numSegments; segment++){
            position[segment][0]++;
        }
    }
    
    public void moveUp(){
        for(int segment = 0; segment< numSegments; segment++){
                position[segment][0]--;
        }
        
    }
    
    public void moveLeft(){
        for(int segment = 0; segment < numSegments; segment++){
            position[segment][1] --;
        }
    }
    
    public void moveRight(){
        for(int segment = 0; segment < numSegments; segment++){
            position[segment][1] ++;
        }
    }
    
    public void unrotate() {   
        
        for (int row = 0; row < numSegments; row++) {
                int relX = position[row][0] - position[1][0];
                int relY = position[row][1] - position[1][1];
                position[row][0] = position[1][0] - relY;
                position[row][1] = position[1][1] + relX;
        }
    }
    
    public void rotate() { 
        
        for (int row = 0; row < numSegments; row++) {
            int relX = position[row][0] - position[1][0];
            int relY = position[row][1] - position[1][1];
            position[row][0] = position[1][0] + relY;
            position[row][1] = position[1][1] - relX;
        }
    }
    
    public int getNumSegments() {
        return numSegments;
    }
    
    public int getColorNumber() {
        return colorNum;
    }
    
    public abstract void initPosition( int centerColumn);    
    
}
