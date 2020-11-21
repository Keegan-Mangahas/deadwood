package deadwood;

public class Deadwood {
    public static void main(String[] args){
        GUI gui = new GUI();
        Gamemaster game = new Gamemaster();
        gui.setVisible(true);

        game.createBoard();
        game.createCards();

        game.numberOfPlayers = gui.askNumPlayers(gui);
        game.createPlayers();
        gui.addTurnButtons();
        game.players = gui.createGuiPlayers(game.players);
    }
}
