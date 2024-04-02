/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisWindow extends JFrame {
    private JMenuBar topMenubar;
    private int gameRows = 20;
    private int gameCols = 12;
    private int windowWidth = gameCols * 20;
    private int windowHeight = gameRows * 20;
    private TetrisDisplay display;
    private TetrisGame game;
    private TetrisLeaderBoard lead;

    public TetrisWindow() {
        
        setTitle("Tetris Game - Precious Adeniyi");
        windowHeight = adjustWidth(windowHeight, gameRows);
        setSize(windowWidth, windowHeight);
        menu_items();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new TetrisGame(gameRows, gameCols);
        display = new TetrisDisplay(game);
        lead = new TetrisLeaderBoard(game);
        
        add(display);
        setVisible(true);
        display.repaint();
    }

    private int adjustWidth(int initialHeight, int numberOfRows) {
        if (numberOfRows < 6) {
            return initialHeight * 2;
        }
        else {
            return initialHeight;
        }
    }
    
    private void menu_items(){
        
        topMenubar = new JMenuBar();
        JMenu file_menu = new JMenu("File");
        
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {    
                game.saveToFile();
            }
        });
        
        JMenuItem loadGame = new JMenuItem("Load Game ");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.retrieveFromFile();
            }
        });
        
        JMenuItem new_game = new JMenuItem("New Game");
        new_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.newGame();
            }
        });

        file_menu.add(new_game);
        file_menu.add(saveGame);
        file_menu.add(loadGame);

        JMenu score_menu = new JMenu("Score");
        JMenuItem highScore = new JMenuItem("High Scores");
        highScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                        lead.score_board());
            }
        });
        
        JMenuItem clearScores = new JMenuItem("Clear Scores");
        clearScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lead.clearScore();
            }
        });
        
        score_menu.add(highScore);
        score_menu.add(clearScores);
        topMenubar.add(file_menu);
        topMenubar.add(score_menu);
        this.setJMenuBar(topMenubar);


        score_menu.add(highScore);
        score_menu.add(clearScores);

        createSpeedMenu(topMenubar); 

        topMenubar.add(file_menu);
        topMenubar.add(score_menu);
        this.setJMenuBar(topMenubar);
}

    private void createSpeedMenu(JMenuBar menuBar) {
        
        JMenu speedMenu = new JMenu("Speed");
        JMenuItem slowMenuItem = createSpeedMenuItem("Slow", 200);
        JMenuItem mediumMenuItem = createSpeedMenuItem("Medium", 100);
        JMenuItem fastMenuItem = createSpeedMenuItem("Fast", 50);

        speedMenu.add(slowMenuItem);
        speedMenu.add(mediumMenuItem);
        speedMenu.add(fastMenuItem);

        menuBar.add(speedMenu);
    }

    private JMenuItem createSpeedMenuItem(String label, int speedValue) {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.addActionListener(e -> setSpeed(speedValue));
        return menuItem;
    }

    private void setSpeed(int newSpeed) {
        display.setSpeed(newSpeed);
    }

    public static void main(String[] args) {
        new TetrisWindow();
    }
}

   