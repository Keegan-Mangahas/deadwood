package deadwood;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class GUI extends JFrame{
    //pane
    JLayeredPane boardPane;
    //labels
    JLabel boardLabel;
    JLabel menuLabel;
    JLabel playerOne;
    JLabel playerTwo;
    JLabel playerThree;
    JLabel playerFour;
    JLabel playerFive;
    JLabel playerSix;
    JLabel playerSeven;
    JLabel playerEight;
    //buttons
    JButton moveButton;
    JButton workButton;
    JButton upgradeButton;

   

    public GUI(){
        super("Deadwood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        boardPane = getLayeredPane();

        boardLabel = new JLabel();
        ImageIcon boardIcon = new ImageIcon("src/images/board.jpg");
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());

        boardPane.add(boardLabel, 0);

        setSize(boardIcon.getIconWidth()+200, boardIcon.getIconHeight());

        /////////////////////////////
        /////BUTTONS/////////////////
        /////////////////////////////

        menuLabel = new JLabel("MENU");
        menuLabel.setBounds(boardIcon.getIconWidth() + 40 , 0, 100, 20);
        boardPane.add(menuLabel, 2);

        moveButton = new JButton("MOVE");
        moveButton.setBackground(Color.white);
        moveButton.setBounds(boardIcon.getIconWidth() + 10, 30, 100, 20);
        moveButton.addMouseListener(new boardMouseListener());

        workButton = new JButton("WORK");
        workButton.setBackground(Color.white);
        workButton.setBounds(boardIcon.getIconWidth() + 10, 60, 100, 20);
        workButton.addMouseListener(new boardMouseListener());

        upgradeButton = new JButton("REHEARSE");
        upgradeButton.setBackground(Color.white);
        upgradeButton.setBounds(boardIcon.getIconWidth() + 10, 90, 100, 20);
        upgradeButton.addMouseListener(new boardMouseListener());

        boardPane.add(moveButton, 2);
        boardPane.add(workButton, 2);
        boardPane.add(upgradeButton, 2);

        /////////////////////////////
        /////PLAYERS/////////////////
        /////////////////////////////
        playerOne = new JLabel();
        ImageIcon playerOneIcon = new ImageIcon("src/images/");
    }

    public void addTurnButtons(){
        this.moveButton.setVisible(true);
        this.workButton.setVisible(true);
        this.upgradeButton.setVisible(true);
    }

    public void removeTurnButtons(){
        this.moveButton.setVisible(false);
        this.workButton.setVisible(false);
        this.upgradeButton.setVisible(false);
    }

    class boardMouseListener implements MouseListener{
        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == moveButton){

            } else if (e.getSource() == workButton){

            } else if (e.getSource() == upgradeButton){
                
            }     
        }
        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {  
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }

    public static void main(String[] args){
        GUI gui = new GUI();
        Gamemaster game = new Gamemaster();

        gui.setVisible(true);
        game.createBoard();
        game.createCards();

        game.numberOfPlayers = askNumPlayers(gui);
        game.createPlayers();
        gui.addTurnButtons();

        while(game.maxGameDays != 0){
            game.distributeSceneCards();
            game.resetPlayers();
            game.resetSets();
            while(game.board.sceneCardsLeft != 1){
                
            }
        }
        

    }

    public static int askNumPlayers(GUI gui){
        Boolean gotInt = false;
        int playerNum = 0;
        String input = JOptionPane.showInputDialog(gui, "How many players? (2-8)");
        while(gotInt == false){
            try {
                playerNum = Integer.parseInt(input);
            } catch (Exception e) {

            }
            if(playerNum >= 2 && playerNum <= 8){
                gotInt = true;
            } else {
                input = JOptionPane.showInputDialog(gui, "Invalid entry, please enter a number 2-8");
                continue;
            }
        }
        return playerNum;
    }
}


