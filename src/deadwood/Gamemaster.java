package deadwood;

import java.util.*;
import org.w3c.dom.Document;

public class Gamemaster {
    protected static Board board = new Board();
    protected static Player currentPlayer;
    protected static boolean boardRandom; //false is default layout, true is randomized layout
    protected static int maxGameDays; //how many days the game lasts
    protected static int numberOfPlayers = 0;
    protected static ArrayList<Player> players = new ArrayList<Player>(); //stores all the players and their data
    protected static ArrayList<Scene> sceneCards = new ArrayList<Scene>(); //stores all the scene cards and their data
    protected static DeadwoodPrinter printer = new DeadwoodPrinter(); //create the printer
    protected static Scanner input = new Scanner(System.in); //global scanner for user inputed
    protected static XMLParser parseXML = new XMLParser(); //xmlParser

    /*
        main method that starts the game
    */
    
    public static void main(String[] args){
        createBoard();
        createCards();
        askNumPlayers();
        createPlayers();
        //actual game!!
        input.nextLine(); //clear scanner
        //while game is running
        while(maxGameDays != 0){
            //first time through, this just sets up board by giving sets scene cards
            //every time after, it is giving sets new scene cards
            //starts at 2 because trailer set is [0] and office set is [1]
            distributeSceneCards();

            //after scene cards are given out return players back to trailer
            //reset players to not working, etc
            resetPlayers();

            //after resetting players, reset each set
            resetSets();

            //while board has more than one scene card
            while(board.sceneCardsLeft != 1){
                runTurn();
                nextPlayer();
            }
            System.out.println("RESTTING BOARD AND PLAYERS");
            maxGameDays--;
        }
        System.out.println("ENDING GAME");
        endGame(printer);

    }
    protected static void createBoard(){
        //read set data and put it into the board
        Document boardDoc = null;
        try{
            boardDoc = parseXML.getDocFromFile("src/xml/board.xml");
            board.sets = parseXML.readBoardData(boardDoc);
        }
        catch (Exception e){
            System.out.println("Error = "+e);
        }
    }

    protected static void createCards(){
        //read and store scene card data
        Document cardDoc = null;
        try{
            cardDoc = parseXML.getDocFromFile("src/xml/cards.xml");
            sceneCards = parseXML.readCardData(cardDoc);
        }
        catch (Exception e){
            System.out.println("Error"+e);
        }
        Collections.shuffle(sceneCards); //shuffles the scene cards
    }

    protected static void askNumPlayers(){
         //ask for amount of players (maybe more error tests?)
         while(numberOfPlayers < 2 || numberOfPlayers > 8){
            printer.askPlayers();
            numberOfPlayers = input.nextInt();
            if(numberOfPlayers < 2 || numberOfPlayers > 8){
                printer.invalidPlayers();
            }
        }
    }

    protected static void distributeSceneCards(){
        for(int i = 2; i < board.sets.size(); i++){
            board.sets.get(i).currentScene = sceneCards.remove(0);
            board.sceneCardsLeft = 10;
        }
    }

    protected static void resetPlayers(){
        for (Player player : players) {
            player.location = "trailer";
            player.onRole = false;
            player.role = null;
            player.rehearsalTokens = 0;
        }
    }

    protected static void resetSets(){
        for (Set set : board.sets) {
            set.sceneDiscovered = false;
            set.sceneWrapped = false;
            for (Role role: set.roles) {
                role.roleTaken = false;
            }
            set.takesLeft = set.totalTakes;
        }
        board.wrapThisSet = null;
    }

    protected static void runTurn(){
        Player current = currentPlayer;
        printer.whoseTurn(currentPlayer);
        board = current.playersTurn(input, printer, board); //update board with data from turn
        if(board.wrapThisSet != null){
            wrapSet(printer);
        }
        
    }

    protected static void nextPlayer(){
        int playerId = currentPlayer.playerNumber;
        if(playerId == numberOfPlayers){
            currentPlayer = players.get(0);
        } else{
            currentPlayer = players.get(playerId);
        }
    }

    private static void wrapSet(DeadwoodPrinter printer){
        Set wrapThis = board.wrapThisSet;
        printer.wrappingScene(wrapThis.currentScene.sceneName, wrapThis.setName);

        int sceneBudget = wrapThis.currentScene.sceneBudget;

        Random die = new Random();
        int[] dieRolls = new int[sceneBudget];
        for(int i = 0; i < dieRolls.length; i++){
            int roll = 1 + die.nextInt(6 - 1 + 1);
            dieRolls[i] = roll;
        }

        int dieRollsIndex = 0;
        for (Player player : players) {
            for (Role role : wrapThis.currentScene.roles) {
                if(player.onRole == true){
                    if(player.role.roleName.equals(role.roleName)){
                        player.dollars += dieRolls[dieRollsIndex];
                        printer.wrapBonusDollar(player.playerNumber, dieRolls[dieRollsIndex]);
                        dieRollsIndex++;
                        player.role = null;
                        player.onRole = false;
                        player.rehearsalTokens = 0;
                    }
                }
                if(dieRollsIndex == sceneBudget){
                    break;
                }
            }
            if(dieRollsIndex == sceneBudget){
                break;
            }
        }
        for (Player player : players) {
            for (Role role : wrapThis.roles) {
                if(player.onRole == true){
                    if(player.role.roleName.equals(role.roleName)){
                        player.dollars += role.roleDifficulty;
                        printer.wrapBonusDollar(player.playerNumber, role.roleDifficulty);
                        player.role = null;
                        player.onRole = false;
                        player.rehearsalTokens = 0;
                    }
                }
            }
        }
    }

    private static int calculateScore(Player player) {
        int score = 0;
        score += player.dollars;
        score += player.credits;
        score += (player.rank * 5);
        return score;
    }

    private static void endGame(DeadwoodPrinter printer) {
        displayScores(calculateScores(), printer);
    }

    private static int[] calculateScores() {
        int[] scores = new int[numberOfPlayers];
        int i = 0;
        for (Player player : players) {
            int score = calculateScore(player);
            scores[i] = score;
            player.score = score;
            score++;
            i++;
        }
        Arrays.sort(scores);
        return scores;
    }

    private static void displayScores(int[] scores, DeadwoodPrinter printer) {
        printer.finalScores();
        int place = 1;
        for(int i = scores.length - 1; i >= 0; i--){
            for (Player player : players) {
                if(player.score == scores[i]){
                    printer.printScores(place, player.playerNumber, player.score);
                    place++;
                }
            }
        }
    }

    public static void createPlayers(){
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
            Player newPlayer = new Player(i, 0, startingCredits, startingRank);
            newPlayer.location = "trailer";
            newPlayer.score = 0;
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
