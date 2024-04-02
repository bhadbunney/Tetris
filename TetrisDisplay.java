/*
Precious Adeniyi
11/6/2023
Tetris part 2: movement and validation of moving brick left and right
*/

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;

public class TetrisDisplay extends JPanel{

    private TetrisGame game;
    private int start_x ;
    private int start_y ;
    private int cell_size = 20;
    private int speed = 100;
    private Timer timer;
    private Color[] colors = {Color.black, Color.GREEN, Color.YELLOW,
                              Color.CYAN,  Color.RED,  Color.MAGENTA,
                              Color.blue, Color.orange };
    private boolean pause = true;
 
    public TetrisDisplay(TetrisGame game){
        
        this.game = game;
     
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                translateKey(e);
            }
        });
        setFocusable(true);

        timer = new Timer(speed, new ActionListener(){ 
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
            }
        });
       timer.start();
    }
    
    private void cycleMove() {
        game.makeMove('D');
        repaint();
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        drawWell(g);
        drawFallingBrick(g);
        drawBackground(g);
        drawText(g);     
    }
    
    private void drawWell (Graphics g){
        
        int well_col = game.getCols();
        int well_row = game.getRows();
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int x_cord = (panelWidth - (well_col * cell_size + 2 * cell_size)) / 2;
        start_y = (panelHeight - (well_row * cell_size + 2 * cell_size)) / 2;
        start_x =  x_cord + cell_size;
        
        g.setColor(Color.black);
        g.fillRect(x_cord, start_y ,cell_size, cell_size * well_row);
        g.fillRect(x_cord + cell_size * well_col+cell_size, start_y ,
                cell_size, cell_size * well_row);
        g.fillRect(x_cord, start_y - cell_size + cell_size * well_row + 
               cell_size,cell_size * well_col + 2 * cell_size, cell_size);
        
        g.setColor(Color.white);
        g.fillRect(x_cord + cell_size, start_y, cell_size * game.getCols()
                , cell_size*well_row);
    } 
    
    private void drawFallingBrick(Graphics g) {
        
        Color brickColor = colors[game.getFallingBrickColor()];
        Color borderColor = colors[0];

        for (int seg = 0; seg < game.getNumSegs(); seg++) {
            g.setColor(brickColor);
            int x_brick = start_x + game.getSegCol(seg) * cell_size;
            int y_brick = (start_y + game.getSegRow(seg) * cell_size);
            g.fillRect(x_brick, y_brick, cell_size, cell_size);
            g.setColor(borderColor);
            g.drawRect(x_brick, y_brick, cell_size, cell_size);
        }
    }
    
    private void drawBackground(Graphics g){
        
        for (int row = 0; row < game.getRows(); row++) {
            for (int col = 0; col < game.getCols(); col++) {
                if (game.fetchBoardPosition(row, col) != 0) {
                    g.setColor(colors[game.fetchBoardPosition(row, col)]);
                    g.fillRect(start_x+col*cell_size,start_y+row*cell_size,
                            cell_size, cell_size);
                    g.setColor(colors[0]);
                    g.drawRect(start_x+col*cell_size, start_y+row*cell_size,
                            cell_size, cell_size);
                }
            }
        }
    } 
    
    private void drawText(Graphics g) {
        
        Font myFont = new Font("Arial", Font.BOLD, 17);
        g.setFont(myFont);

        g.setColor(Color.black);
        g.drawRect(0, 0, 130, 30);
        g.setColor(Color.white);
        g.fillRect(0, 0, 130, 30);
        g.setColor(Color.black);
        g.drawString("Score: " + game.getScore(), 10, 20);
 
        if (game.getState() == 0) {
            Font gameOverFont = new Font("Arial", Font.BOLD, cell_size*2);
            g.setFont(gameOverFont);
            g.setColor(Color.black);
            g.drawRect(start_x - 3*cell_size , start_y + cell_size * 5,
                    cell_size*(game.getCols() + 6), cell_size * 5);
            g.setColor(Color.white);
            g.fillRect(start_x - 3*cell_size, start_y + cell_size * 5,
                    cell_size*(game.getCols() + 6), cell_size * 5);
            g.setColor(Color.blue);
            g.drawString("Game Over!", start_x , start_y + cell_size * 8);
        }
}
    
    private void translateKey(KeyEvent e){
        
        int key_Code = e.getKeyCode();
        switch (key_Code){
            case KeyEvent.VK_UP:
                game.makeMove('U');
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove('R');
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove('L');
                break;   
            case KeyEvent.VK_SPACE:
                pauseTimer();
                break;
            case KeyEvent.VK_N:
                game.newGame();
                break;    
        }
        repaint();
    }
    
    private void pauseTimer() {
        
        if (pause == true) {
            timer.stop();
            pause = false;
        }
        else {
            timer.start();
            pause = true;
        }
    }
    
    public void setSpeed(int newSpeed) {
        speed = newSpeed;
        timer.setDelay(speed);
    }
}
 

