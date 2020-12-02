package deadwood;

import java.util.*;

public class Board extends Gamemaster{
    protected int sceneCardsLeft;
    protected static int currentDay; //what day the game is on
    protected ArrayList<Set> sets = new ArrayList<Set>();
    protected Set wrapThisSet;

    /**
     * Default Constructor
     */
    public Board() {
    }

    private void advanceDay() {
        currentDay++;
    }

    private void wrapScene(Scene scene){
        sceneCardsLeft--;
        /*
        if day is over, run endDay. if last day,
        run gamemaster.endGame()
        */
    }

    //resets board and prepares game for next day
    // could be part of Gamemaster class
    private void endDay(){

    }

    //checks if day is over
    private boolean isDayOver(){
        return false;   
    }

    //checks if game is over
    private boolean isGameOver(){
        return false;
    }
}
