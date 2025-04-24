import java.io.Serializable;

public class ScoreBox implements Serializable {
    private String nickname;
    private int time;
    private int score;
    public ScoreBox(String nickname, int score, int time){
        this.score=score;
        this.nickname=nickname;
        this.time=time;
    }
    public String getNickname(){
        return nickname;
    }
    public int getTime(){
        return time;
    }
    public int getScore(){
        return score;
    }

    @Override
    public String toString() {
        return "Wynik gracza: "+nickname+" to "+score+" w czasie "+time+"s";
    }
}
