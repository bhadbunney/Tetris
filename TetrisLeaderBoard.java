/*
Precious Adeniyi
11/28/2023
Tetris part 3: Leaderboard and scoring implementation
*/

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class TetrisLeaderBoard {
    
    private  int max_score = 10;
    private TetrisGame game;
    private ArrayList<Integer> topScores;
    private ArrayList<String> playerNames = new ArrayList<>();

    public TetrisLeaderBoard(TetrisGame game) {
        this.game = game;
        this.topScores = new ArrayList<>();
        loadScoresFromFile();
        handleScoreUpdate();      
    }
    public void handleScoreUpdate() {
        
        if (shouldUpdateScore(game.getScore())) {
            updateTopScores("PlayerName", game.getScore());
            String score_name = JOptionPane.showInputDialog("Congratulations"
                            + " you made it to the leaderboard! enter your name");
            recordScore(score_name, game.getScore());
        }
    }
    private boolean shouldUpdateScore(int newScore) {
        
        int lowestScore = (topScores.size() > 0) ?
                        topScores.get(topScores.size() - 1) : Integer.MIN_VALUE;
        return (topScores.size() < max_score || newScore > lowestScore);
    }


    private void loadScoresFromFile() {
          
        topScores.clear();
        playerNames.clear();
        try {
            File file = new File("Score.dat");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(": ");
                if (parts.length == 2 && parts[1].matches("\\d+")) {
                    playerNames.add(parts[0]);
                    topScores.add(Integer.parseInt(parts[1]));
                }
            }
            organizeScores();
            writeScoresToFile();

            scanner.close();
        }
        catch (FileNotFoundException e) {
           JOptionPane.showMessageDialog(null, 
                        "Trouble opening file to read: ");
        }
    }

    private void recordScore(String playerName, int score) {

        try {
            FileWriter outWriter = new FileWriter("Score.dat", true);
            outWriter.write(playerName + ": " + score + "\n");
            outWriter.close();
        } 
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, 
                        "Trouble writing to file: Score.dat");
        }
    }

    private void updateTopScores(String playerName, int newScore) {
        
        if (topScores.size() < max_score) {
            topScores.add(newScore);
            playerNames.add(playerName);
            Collections.sort(topScores, Collections.reverseOrder());
        }
        else {
            int lowestScore = topScores.get(topScores.size() - 1);
            if (newScore > lowestScore) {
                topScores.remove(topScores.size() - 1);
                playerNames.remove(playerNames.size() - 1);
                topScores.add(newScore);
                playerNames.add(playerName);
                Collections.sort(topScores, Collections.reverseOrder());
            }
        }
    }

    private void writeScoresToFile() {
        
        try {
            FileWriter file = new FileWriter("Score.dat");
            PrintWriter writer = new PrintWriter(file);

            for (int i = 0; i < topScores.size(); i++) {
                writer.println(playerNames.get(i) + ": " 
                                + topScores.get(i));
            }
            writer.flush();
            writer.close();
            file.close();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                        "Trouble writing to file: Score.dat");
        }
    }
    private void organizeScores() {
        
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            sortedScores.add(new AbstractMap.SimpleEntry<>(playerNames.get
                                (i), topScores.get(i)));
        }
        Collections.sort(sortedScores, (o1, o2) -> o2.getValue().
                            compareTo(o1.getValue()));

        topScores.clear();
        playerNames.clear();

        for (int i = 0; i < Math.min(max_score, sortedScores.size()); i++) {
            topScores.add(sortedScores.get(i).getValue());
            playerNames.add(sortedScores.get(i).getKey());
        }
    }

    public String score_board() {
        
        StringBuilder leaderboard = new StringBuilder();
        loadScoresFromFile();
        for (int i = 0; i < topScores.size(); i++) {
            leaderboard.append("Score ").append(i + 1).append(": ")
                       .append(playerNames.get(i)).append(": ")
                       .append(topScores.get(i)).append("\n");
        }
        return leaderboard.toString();
        
    }

    public void clearScore(){
        
        try{
            FileWriter file = new FileWriter("Score.dat", false);
            PrintWriter flusher = new PrintWriter(file, false);
            flusher.flush();
            flusher.close();
            file.close();
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, 
                        "Trouble writing to file: Score.dat");
        }
    }
}


