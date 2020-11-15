package deadwood;

import java.util.*;
import org.w3c.dom.Document;

public class Gamemaster {
    protected Board board;
    protected Player currentPlayer;
    protected boolean boardRandom; //false is default layout, true is randomized layout
    protected int maxGameDays; //how many days the game lasts
    protected int numberOfPlayers;
    protected ArrayList<Player> players = new ArrayList<Player>(); //stores all the players and their data
    protected ArrayList<Scene> sceneCards = new ArrayList<Scene>(); //stores all the scene cards and their data

    /*
        main method that starts the game
    */
    public static void main(String[] args){
        Gamemaster game = new Gamemaster(); //create the game!
        Board board = new Board(); //creates the board!
        DeadwoodPrinter printer = new DeadwoodPrinter(); //create the printer
        Scanner input = new Scanner(System.in); //global scanner for user inputed
        XMLParser parseXML = new XMLParser(); //xmlParser

        /*
        setup board
        make a while loop to cycle through players turns until 1 scene card is left
        */
        
        //read set data and put it into the board
        Document boardDoc = null;
        try{
            boardDoc = parseXML.getDocFromFile("src/xml/board.xml");
            board.sets = parseXML.readBoardData(boardDoc);
        }
        catch (Exception e){
            System.out.println("Error = "+e);
        }

        //read and store scene card data
        Document cardDoc = null;
        try{
            cardDoc = parseXML.getDocFromFile("src/xml/cards.xml");
            game.sceneCards = parseXML.readCardData(cardDoc);
        }
        catch (Exception e){
            System.out.println("Error"+e);
        }
        Collections.shuffle(game.sceneCards); //shuffles the scene cards
        
        //actual game!
        //ask for amount of players (maybe more error tests?)
        game.numberOfPlayers = 0;
        while(game.numberOfPlayers < 2 || game.numberOfPlayers > 8){
            printer.askPlayers();
            game.numberOfPlayers = input.nextInt();
            if(game.numberOfPlayers < 2 || game.numberOfPlayers > 8){
                printer.invalidPlayers();
            }
        }
 
        game.createPlayers();
        input.nextLine(); //clear scanner
        //while game is running
        while(true){
            //first time through, this just sets up board by giving sets scene cards
            //every time after, it is giving sets new scene cards
            //starts at 2 because trailer set is [0] and office set is [1]
            for(int i = 2; i < board.sets.size(); i++){
                board.sets.get(i).currentScene = game.sceneCards.remove(0);
            }

            //test to see sets and their scene card info
            for(int i = 2; i < board.sets.size(); i++){
                System.out.printf("Set: %s | Scene Card: %s%n", board.sets.get(i).setName, board.sets.get(i).currentScene.sceneName);
            }

            //while board has more than one scene card
            while(true){
                Player current = game.currentPlayer;
                printer.whoseTurn(game.currentPlayer);
                String turnResult = current.playersTurn(input, printer);
                if(turnResult.equals("next")){
                    int playerId = current.playerNumber;
                    if(playerId == game.numberOfPlayers){
                        game.currentPlayer = game.players.get(0);
                }
                    else{
                        game.currentPlayer = game.players.get(playerId);
                    }
                }
            }
            
        }
    }

    private int calculateScore(Player player) {
        int score = 0;
        score += player.getDollars();
        score += player.getCredits();
        score += (player.getRank() * 5);
        return score;
    }

    private void endGame() {
        // TODO: implement endGame
    }

    private Player calculateWinner() {
        // TODO: implement calculateWinner
        return null;
    }

    private void displayWinner(Player winner) {
        // TODO: implement displayWinner
    }

    private void setBoardLayout(){
        // TODO: implement setBoardLayout()
        //ask user if board should be default or randomized
        //create board layout, default or random
    }

    private void createPlayers(){
        //create players with stats accordingly
        int startingCredits = 0;
        int startingRank = 1;
        if(numberOfPlayers <= 3){
            maxGameDays = 3;
        } else if(numberOfPlayers == 4){
            maxGameDays = 4;
        } else if(numberOfPlayers == 5){
            maxGameDays = 4;
            startingCredits = 2;
        } else if(numberOfPlayers == 6){
            maxGameDays = 4;
            startingCredits = 4;
        } else{
            maxGameDays = 4;
            startingRank = 2;
        }
        for(int i = 1; i <= numberOfPlayers; i++){
            Player newPlayer = new Player(i, 100, startingCredits, startingRank);
            players.add(newPlayer);
        }
        for(int i = 0; i < numberOfPlayers; i++){
            Player current = players.get(i);
            String print = current.printPlayerData();
            System.out.println(print);
        }

        //player 1 goes first cause that's easiest
        currentPlayer = players.get(0);
    }
}
