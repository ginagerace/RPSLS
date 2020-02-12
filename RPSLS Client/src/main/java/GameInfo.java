import java.io.Serializable;

public class GameInfo implements Serializable {

    int p1score;
    int p2score;
    String p1plays;
    String p2plays;
    String choice;
    String message;
    boolean again1;
    boolean again2;
    boolean twoClients;

    GameInfo(){
        p1score = 0;
        p2score = 0;
        p1plays = "";
        p2plays = "";
        message = "";
        choice = "";
        twoClients = false;
        again1 = false;
        again2 = false;
    }

    void update(int num){
        if(num == 1)
            p1plays = choice;
        else
            p2plays = choice;
        choice = "";
    }

    void playAgain(int num){
        if(num == 1)
            again1 = true;
        else
            again2 = true;
    }

    //update score and returns winning player number (returns 0 if draw)
    int roundWinner() {
        int win;
        if(p1plays.equals(p2plays))
            win = 0;
        else if(p1plays.equals("rock") && (p2plays.equals("lizard") || p2plays.equals("scissors"))) {
            p1score++;
            win = 1;
        }
        else if(p1plays.equals("lizard") && (p2plays.equals("spock") || p2plays.equals("paper"))){
            p1score++;
            win = 1;
        }
        else if(p1plays.equals("spock") && (p2plays.equals("scissors") || p2plays.equals("rock"))){
            p1score++;
            win = 1;
        }
        else if(p1plays.equals("scissors") && (p2plays.equals("paper") || p2plays.equals("lizard"))){
            p1score++;
            win = 1;
        }
        else if(p1plays.equals("paper") && (p2plays.equals("spock") || p2plays.equals("rock"))){
            p1score++;
            win = 1;
        }
        else{
            p2score++;
            win = 2;
        }
        p1plays = "";
        p2plays = "";
        return win;
    }

    boolean bothPlayed(){
        if(p1plays.equals("") || p2plays.equals(""))
            return false;
        return true;
    }
}
