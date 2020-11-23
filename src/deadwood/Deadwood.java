package deadwood;

public class Deadwood {
        private static GUI gui = new GUI();
        private static Gamemaster game = new Gamemaster();
        
    public static void main(String[] args){
        
        gui.setVisible(true);

        game.createBoard();
        game.createCards();

        game.numberOfPlayers = gui.askNumPlayers(gui);
        game.createPlayers();

        game.distributeSceneCards(); //TODO: need to make this happen when day resets
        gui.addGUISceneCards(game.board.sets); //TODO: need to make this happen when day resets

        game.resetPlayers(); //TODO: need to make this happen when day resets
        game.resetSets(); //TODO: need to make this happen when day resets

        game.currentPlayer.tempBoard = game.board; //TODO: need to set this up to update main board after each turn DONE??
        
        game.players = gui.createGuiPlayers(game.players);
        gui.runTurn(game.currentPlayer);
        
    }
    public static void endTurn(){
        game.board = game.currentPlayer.tempBoard; //update board

        int playerId = game.currentPlayer.playerNumber;
        if(playerId == game.numberOfPlayers){
            game.currentPlayer = game.players.get(0);
        } else{
            game.currentPlayer = game.players.get(playerId);
        }
        
        game.currentPlayer.tempBoard = game.board; //give next player updated board;

        game.currentPlayer.resetBeforeTurn();
        gui.runTurn(game.currentPlayer);
    }

    public static void movePlayer(){
        gui.addMoveOptions(game);
    }
    
    public static void upgradePlayer(){
        /*
        if(game.currentPlayer.rank == 6){
            gui.showInvalid();
        } else if("office".equals(game.currentPlayer.location)){
            gui.addUpgradeOptions(game);
        } else{
            gui.showInvalid();
        }
        */ //TODO: UNCOMMENT THIS
        gui.addUpgradeOptions(game);
    }

    public static void back(){
        gui.runTurn(game.currentPlayer);
    }

    public static void movePlayerHere(String location){
        game.currentPlayer.location = location;
        System.out.println(game.currentPlayer.printPlayerData());
        game.currentPlayer.movedTo(game.printer);
        

        game.currentPlayer.moved = true;

        gui.updatePlayerLocation(game.currentPlayer, game.board.sets);
        gui.revealSceneCard(game.currentPlayer, game.board.sets);
        gui.runTurn(game.currentPlayer);
    }
}
