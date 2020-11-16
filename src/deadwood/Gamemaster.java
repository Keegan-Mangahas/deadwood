package deadwood;

import java.util.*;

import org.w3c.dom.Document;

public class Gamemaster {
    private Board board;
    private PlayerController currentPlayerController;
    private boolean boardRandom; //false is default layout, true is randomized layout
    private int numberOfPlayers;
    private ArrayList<PlayerController> playerControllers = new ArrayList<PlayerController>(); //stores all the players and their data
    // TODO: arguable, sceneCards
    // need some sort of Scene controller
    private ArrayList<Scene> sceneCards = new ArrayList<Scene>(); //stores all the scene cards and their data
    public static Scanner scanner;

    /* Constructor Singleton */
    public Gamemaster() {

    }

    /*
        main method that starts the game
    */
    public static void main(String[] args) {
        Gamemaster game = new Gamemaster(); //create the game!
        DeadwoodPrinter printer = new DeadwoodPrinter(); //create the printer
        scanner = new Scanner(System.in); //global scanner for user inputted

        /*
        setup board
        make a while loop to cycle through players turns until 1 scene card is left
        */

        XMLParser parseXML = new XMLParser();

        /*
        //read board data
        Document boardDoc = null;
        try{
            boardDoc = test.getDocFromFile("src/xml/board.xml");
            test.readBoardData(boardDoc);
        }
        catch (Exception e){
            System.out.println("Error = "+e);
        }
        */

        //read and store scene card data
        Document cardDoc = null;
        try {
            cardDoc = parseXML.getDocFromFile("src/xml/cards.xml");
            game.sceneCards = parseXML.readCardData(cardDoc);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        Collections.shuffle(game.sceneCards); //shuffles the scene cards

        /*
        This reads data out from each scene card and its roles
        for(int i = 0; i < game.sceneCards.size(); i++){
            Scene sceneTest = game.sceneCards.get(i);
            sceneTest.printSceneInfo();
            for(int j = 0; j < sceneTest.roles.size(); j++){
                Role roleTest = sceneTest.roles.get(j);
                roleTest.printRoleData();
            }
        }
        */


        //actual game!
        //ask for amount of players (maybe more error tests?)
        game.numberOfPlayers = 0;
        while (game.numberOfPlayers < 2 || game.numberOfPlayers > 8) {
            printer.askPlayers();
            game.numberOfPlayers = input.nextInt();
            if (game.numberOfPlayers < 2 || game.numberOfPlayers > 8) {
                printer.invalidPlayers();
            }
        }

        game.createPlayers();
        input.nextLine(); //clear scanner
        //while board has more than one scene card
        while (true) {
            PlayerController current = game.currentPlayerController;
            printer.whoseTurn(game.currentPlayerController);
            String turnResult = current.performTurn(input, printer);
            if (turnResult.equals("next")) {
                int playerId = current.getPlayerNumber();
                if (playerId == game.numberOfPlayers) {
                    game.currentPlayerController = game.playerControllers.get(0);
                } else {
                    game.currentPlayerController = game.playerControllers.get(playerId);
                }
            }
        }
    }

    private int calculateScore(PlayerController playerController) {
        int score = 0;
        score += playerController.getDollars();
        score += playerController.getCredits();
        score += (playerController.getRank() * 5);
        return score;
    }

    private void endGame() {
        // TODO: implement endGame
    }

    private PlayerController calculateWinner() {
        // TODO: implement calculateWinner
        return null;
    }

    private void displayWinner(PlayerController winner) {
        // TODO: implement displayWinner
    }

    private void setBoardLayout() {
        // TODO: implement setBoardLayout()
        //ask user if board should be default or randomized
        //create board layout, default or random
    }

    private void createPlayers() {
        //create players with stats accordingly
        int startingCredits = 0;
        int startingRank = 1;
        if (numberOfPlayers <= 3) {
            Board.BoardData.gameLength = 3;
        } else if (numberOfPlayers == 4) {
            Board.BoardData.gameLength = 4;
        } else if (numberOfPlayers == 5) {
            Board.BoardData.gameLength = 4;
            startingCredits = 2;
        } else if (numberOfPlayers == 6) {
            Board.BoardData.gameLength = 4;
            startingCredits = 4;
        } else {
            Board.BoardData.gameLength = 4;
            startingRank = 2;
        }
        for (int i = 1; i <= numberOfPlayers; i++) {
            PlayerController newPlayerController = new PlayerController(i, 100, startingCredits, rehearsalTokens, startingRank);
            playerControllers.add(newPlayerController);
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerController current = playerControllers.get(i);
            String print = current.printPlayerData();
            System.out.println(print);
        }

        //player 1 goes first cause that's easiest
        currentPlayerController = playerControllers.get(0);
    }
}
