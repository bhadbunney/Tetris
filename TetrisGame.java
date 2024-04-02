/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

import java.util.*;
import javax.swing.*;
import java.io.*;

public class TetrisGame implements SavableGame {
    
    private TetrisBrick fallingBrick;   
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int state = 1;
    private Random randomGen;
    private int[][] background;
    private int score;

    public TetrisGame(int rows, int cols) {
        
        this.rows = rows;
        this.cols = cols;
        randomGen = new Random();
        spawnBrick();
        background = new int[rows][cols];

    }
    
    public String toString(){
        
        String board = rows +"  "+cols+"   "+score+"\n";
        for(int row = 0; row < background.length; row++){
            for(int col = 0; col < background[0].length;col++){
                board += background[row][col]+" ";
            }
            board += "\n";
        }
        board = board.substring(0,board.length()-1);
        return board;
    }
    
    private void spawnBrick() {
        
        if(state == 1){
        int brick = randomGen.nextInt(numBrickTypes)+1;
        int centerCol = (cols) / 2;
            if (brick == 1)
                fallingBrick = new ElBrick(centerCol);
            if (brick == 2)
                fallingBrick = new EssBrick(centerCol);
            if (brick == 3)
                fallingBrick = new JayBrick(centerCol);
            if (brick == 4)
                fallingBrick = new LongBrick(centerCol);
            if (brick == 5)
                fallingBrick = new SquareBrick(centerCol);
            if (brick == 6)
                fallingBrick = new StackBrick(centerCol);
            if (brick == 7)
                fallingBrick = new ZeeBrick(centerCol);
        }    
    }  
    
    private boolean validateMove() {
 
        for (int seg = 0; seg < getNumSegs(); seg++) {
            int row = getSegRow(seg);
            int col = getSegCol(seg);
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                return false;
            }                         
            if (fetchBoardPosition(row, col) != 0) {
                return false;
            }
        }
        return true; 
        
    }
   
    public void makeMove(char move) {
  
        if(state == 1){
        if (move == 'D') {   
            fallingBrick.moveDown();
            if(validateMove() == false){ 
                transferColor();
                rowDelete();              
                if (!checkGameOver()) {
                     spawnBrick();
                }
            }
            } 
        
        if(move == 'R'){
            fallingBrick.moveRight();
            if(validateMove() ==false){
                fallingBrick.moveLeft();
            }
        }
        
        if(move == 'L'){
            fallingBrick.moveLeft();
            if(validateMove() ==false){
                fallingBrick.moveRight();
            }
        }
        
       if (move == 'U') {
            if (!(fallingBrick instanceof SquareBrick)) {
                fallingBrick.rotate();
                if (validateMove() ==false) {
                    fallingBrick.unrotate();
                }
            }
        }
        }
        
    }

    public int getFallingBrickColor() {
        return fallingBrick.colorNum;
    }
    public int fetchBoardPosition(int row, int col){
        return background[row][col];
    }
    
    public int getNumSegs() {
        return fallingBrick.getNumSegments();
    }
    
    public int getSegRow(int seg_number) {
        return fallingBrick.position[seg_number][0];
    }

    public int getSegCol(int seg_number) {
        return fallingBrick.position[seg_number][1];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    public int getState(){
        return state;
    }
    public int getScore(){
        return score;
    }
    
    private boolean checkGameOver() {
        
        for (int seg = 0; seg < getNumSegs(); seg++) {
            int col = getSegCol(seg);
            if (background[2][col] != 0 && getState() == 1) {
                state = 0;
                fallingBrick.moveUp();
                checkGameState();
                return true;
            }
        }
        return false;
    }

    
    private void checkGameState() {
        if (getState() == 0) {
            TetrisLeaderBoard leaderboard = new TetrisLeaderBoard(this);
        }
    }
    
    private void transferColor() {
        
            for (int seg = 0; seg < getNumSegs(); seg++) {
                int row = getSegRow(seg)-1;
                int col = getSegCol(seg);
                background[row][col] = getFallingBrickColor(); 
            }  
    }

    public void initBoard() {
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                background[row][col] = 0;
            }
        }

    }

    public void newGame(){
        
        state = 1;    
        initBoard();
        spawnBrick();
        score = 0;
    }
    
    private boolean rowHasSpace(int rowNumber) {
        
        for (int col = 0; col < cols; col++) {
            if (background[rowNumber][col] == 0) {
                return true;
            }
        }
        return false;
    }

    private void copyRow(int sourceRow, int destRow) {
        
        for (int col = 0; col < cols; col++) {
            background[destRow][col] = background[sourceRow][col];
        }
    }

    private void copyAllRows(int rowNumber) {
        
        for (int row = rowNumber; row >= 1; row--) {
            copyRow(row - 1, row);
        }
    }
    
    private int rowDelete() {
        
        int deletedRows = 0;
        int startRow = 0;
        for (int row = 0; row < rows; row++) {
            if (!rowHasSpace(row)) {
                if (startRow == 0) {
                    startRow = row;
                }
                deletedRows++;
                copyAllRows(row);
            }
        }
        scoreMatching(deletedRows);
        return deletedRows;
    }

    private  int scoreMatching(int deletedRows) {
        
        switch (deletedRows) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
        }
        return score;
    }

    
    public void saveToFile(){
        
        String file_Name = JOptionPane.showInputDialog
                                ("Enter the file name to save as \n");
        File fileConnection = new File(file_Name+".dat");
        if(fileConnection.exists() && !fileConnection.canWrite()){
            System.err.print("Trouble opening to Write, file: "+file_Name+".dat");
            return;
        }
        try{
            FileWriter outWriter =new FileWriter (fileConnection);
            outWriter.write(this.toString());
            outWriter.close();
        }
        catch (IOException ioe){
            JOptionPane.showMessageDialog(null, 
                        "Trouble writing to file: ");
        }
    }
    
    public void retrieveFromFile() {
        
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                Scanner inScan = new Scanner(selectedFile);
                rows = inScan.nextInt();
                cols = inScan.nextInt();
                score = inScan.nextInt();
                background = new int[rows][cols];
                state = 1;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        background[row][col] = inScan.nextInt();
                    }
                }
                inScan.close();
            }
            catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, 
                        "Trouble opening file to read: ");
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        "file chosen is not supported");
            }
        }
    }
   
}
