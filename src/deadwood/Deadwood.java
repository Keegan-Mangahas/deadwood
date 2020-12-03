package deadwood;

import java.util.*;

public class Deadwood {
        private static GUI gui = new GUI();
        private static Gamemaster game = new Gamemaster();
        
    public static void main(String[] args){
        
        gui.setVisible(true);

        game.createBoard();
        game.createCards();

        game.numberOfPlayers = gui.askNumPlayers(gui);
        game.createPlayers();

        game.distributeSceneCards();
        gui.addGUISceneCards(game.board.sets);

        game.resetPlayers(); 
        game.resetSets(); 

        game.currentPlayer.tempBoard = game.board; 

        game.players = gui.createGuiPlayers(game.players);
        gui.runTurn(game.currentPlayer);
    }

    public static void act(){
        Random die = new Random();
        int dieRoll = 1 + die.nextInt(6 - 1 + 1);

        Set findSet = new Set();
        int setIndex = 0;
        for (Set getSet : game.board.sets) {
            if(game.currentPlayer.location.equals(getSet.setName)){
                findSet = getSet;
                break;
            }
            setIndex++;
        }

        int rollTotal = dieRoll + game.currentPlayer.rehearsalTokens;
        if(rollTotal >= findSet.currentScene.sceneBudget){
            findSet.takesLeft--;
            gui.addTake(findSet);
            if(game.currentPlayer.role.starring == true){
                game.currentPlayer.credits += 2;
                gui.showActSuccess(0, 2);
            } else if(game.currentPlayer.role.starring == false) {
                game.currentPlayer.credits += 1;
                game.currentPlayer.dollars += 1;
                gui.showActSuccess(1, 1);
            }
            if(findSet.takesLeft == 0){
                game.board.sceneCardsLeft--;
                findSet.sceneWrapped = true;
                game.board.sets.set(setIndex, findSet);
                game.board.wrapThisSet = findSet; 
                wrapSet();
            }
        } else {
            if(game.currentPlayer.role.starring == true){
                gui.showActFail(0, 0);
            } else if (game.currentPlayer.role.starring == false){
                game.currentPlayer.dollars += 1;
                gui.showActFail(1, 0);
            }
        }
        endTurn();
    }

    public static void wrapSet(){
        Set wrapThis = game.board.wrapThisSet;
        int sceneBudget = wrapThis.currentScene.sceneBudget;

        Random die = new Random();
        int[] dieRolls = new int[sceneBudget];
        for(int i = 0; i < dieRolls.length; i++){
            int roll = 1 + die.nextInt(6 - 1 + 1);
            dieRolls[i] = roll;
        }

        int dieRollIndex = 0;
        for (Player player : game.players) {
            for (Role role : wrapThis.roles) {
                if(player.onRole == true){
                    if(player.role.roleName.equals(role.roleName)){
                        player.dollars += dieRolls[dieRollIndex];
                        dieRollIndex++;
                        player.role = null;
                        player.onRole = false;
                        player.rehearsalTokens = 0;
                    }
                }
                if(dieRollIndex == sceneBudget){
                    break;
                }
            }
            if(dieRollIndex == sceneBudget){
                break;
            }
        }
        for (Player player : game.players) {
            for (Role role : wrapThis.currentScene.roles) {
                if(player.onRole == true){
                    if(player.role.roleName.equals(role.roleName)){
                        player.dollars += role.roleDifficulty;
                        player.role = null;
                        player.onRole = false;
                        player.rehearsalTokens = 0;
                    }
                }
            }
        }
        gui.wrapSceneCard(game.board.sets);
    }

    public static void rehearse(){
        Set findSet = new Set();
        for (Set getSet : game.board.sets) {
            if(game.currentPlayer.location.equals(getSet.setName)){
                findSet = getSet;
            }
        }
        if(game.currentPlayer.rehearsalTokens + 1 == findSet.currentScene.sceneBudget ){
            gui.showInvalidRehearse();
            gui.runTurn(game.currentPlayer);
        } else {
            game.currentPlayer.rehearsalTokens++;
            gui.showRehearseSuccess();
            endTurn();
        }
        
    }

    public static void workPlayer(){
        if(!"trailer".equals(game.currentPlayer.location) && !"office".equals(game.currentPlayer.location)){
            for (Set set : game.currentPlayer.tempBoard.sets) {
                if(set.sceneWrapped == false && set.setName.equals(game.currentPlayer.location)){
                    gui.showRoleOptions(set);
                    return;
                } 
            }
        } else{
            gui.showInvalid();
            gui.runTurn(game.currentPlayer);
            return;
        }
        gui.showInvalid();
        gui.runTurn(game.currentPlayer);
    }

    public static void addToRole(String roleName){
        int setIndex = 0;
        Set playerSet = new Set();
        for (Set set : game.currentPlayer.tempBoard.sets) {
            if(game.currentPlayer.location.equals(set.setName)){
                playerSet = set;
                break;
            }
            setIndex++;
        }

        Role playerRole = new Role();
        int nonStarIndex = 0;
        int starIndex = 0;
        for (Role role : playerSet.roles) {
            if(roleName.equals(role.roleName)){
                playerRole = role;
                break;
            }
            nonStarIndex++;
        }
        for (Role role : playerSet.currentScene.roles) {
            if(roleName.equals(role.roleName)){
                playerRole = role;
                break;
            }
            starIndex++;
        }
        if(playerRole.roleTaken == true || playerRole.roleDifficulty > game.currentPlayer.rank){
            gui.showInvalid();
            gui.runTurn(game.currentPlayer);
            return;
        }
        playerRole.roleTaken = true;
        game.currentPlayer.role = playerRole;
        game.currentPlayer.onRole = true;

        if(game.currentPlayer.role.starring == false){
            Set tempSet = game.board.sets.get(setIndex);
            tempSet.roles.set(nonStarIndex, playerRole);
            game.board.sets.set(setIndex, tempSet);
        } else {
            Set tempSet = game.board.sets.get(setIndex);
            tempSet.currentScene.roles.set(starIndex, playerRole);
            game.board.sets.set(setIndex, tempSet);
        }
        gui.putPlayerOnRole(game.currentPlayer, game.board.sets);
        endTurn();

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
        if(game.board.sceneCardsLeft == 9){ //TODO: MAKE DEFAULT
            game.board.maxGameDays--;
            if(game.board.maxGameDays == 2){
                int[] scores = game.calculateScores();
                gui.displayScores(scores, game.players);
                return;
            }
            game.resetPlayers();
            game.resetSets();
            gui.resetForNextDay(game);
            game.distributeSceneCards();
            gui.addGUISceneCards(game.board.sets); 
            gui.dayReset();
        }
        gui.showNextPlayersTurn();
        gui.runTurn(game.currentPlayer);
    }


    public static void movePlayer(){
        gui.addMoveOptions(game);
    }
    
    public static void upgradePlayer(){
        if(game.currentPlayer.rank == 6){
            gui.showInvalid();
        } else if("office".equals(game.currentPlayer.location)){
            gui.addUpgradeOptions(game);
        } else{
            gui.showInvalid();
        }
    }

    public static void upgradePlayerRank(int rank, String currency){
        System.out.println("Rank: "+game.currentPlayer.rank+"Credits "+game.currentPlayer.credits);
        game.currentPlayer.upgraded = game.currentPlayer.upgradeRank(rank, currency);
        System.out.println("Rank: "+game.currentPlayer.rank+"Credits "+game.currentPlayer.credits);
        if(game.currentPlayer.upgraded == false){
            gui.insufficent();
        }
        if(game.currentPlayer.upgraded == true){ 
            gui.changePlayerRankGUI(game.currentPlayer);
        }
        gui.runTurn(game.currentPlayer);
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
