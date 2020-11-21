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
        gui.addTurnButtons();
        game.players = gui.createGuiPlayers(game.players);
        
        

        
    }
    public static void movePlayer(){
        gui.addMoveOptions(game);
    }

    public static Boolean checkTurnStatus(String action){
        switch(action){
            case "move":
                if(game.currentPlayer.moved == false){
                    return true;
                } else {return false;}
            case "upgrade":
                if(game.currentPlayer.upgraded == false){
                    return true;
                } else { return false;}
            case "work":
                if(game.currentPlayer.onRole == false){
                    return true;
                } else {return false;}
            default:
                return false;
        }
    }
}
