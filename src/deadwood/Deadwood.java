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
