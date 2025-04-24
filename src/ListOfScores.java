import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListOfScores {
    private static final String DATA = "data.txt";
    private List<ScoreBox> scores;
    public ListOfScores(){
        scores=new ArrayList<>();
        loadScores();
    }
    public void addScore(ScoreBox score){
        scores.add(score);
        scores.sort((score1,score2)->Integer.compare(score2.getScore(),score1.getScore()));
        saveScores();
    }
    public List<ScoreBox> getScores(){
        return scores;
    }
    private void loadScores(){
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(DATA))){
            scores = (List<ScoreBox>) input.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private void saveScores(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATA))){
            output.writeObject(scores);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
