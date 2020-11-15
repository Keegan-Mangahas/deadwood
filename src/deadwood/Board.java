package deadwood;

import java.util.*;

public class Board extends Gamemaster{
    protected int sceneCardsLeft;
    protected static int currentDay; //what day the game is on
    protected ArrayList<Set> sets = new ArrayList<Set>();

    /**
     * Default Constructor
     */
    public Board() {
    }


    // TODO: find out if this is necessary. Currently, Gamemaster has currentPlayer information, which Board
    // should be able to call. Is Board an extension of Gamemaster?
//    private Player getActivePlayer() {
//        return null;
//    }

    // TODO: scrap?
//    private Set[] getRoom(){
//        return null;
//    }

    /**
     * advanceDay
     *
     * Advances the current day.
     */
    private void advanceDay() {
        currentDay++;
    }

    /**
     * wrapScene
     *
     * TODO: descriptive description for wrapScene
     * @param scene scene object to wrap
     */
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
