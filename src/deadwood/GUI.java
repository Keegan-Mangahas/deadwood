package deadwood;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.*;

public class GUI extends JFrame{
    //pane
    JLayeredPane boardPane;
    //labels
    JLabel boardLabel;
    JLabel menuLabel;
    //buttons
    JButton moveButton;
    JButton workButton;
    JButton upgradeButton;
    JLabel playerLabel;

    public static ArrayList<JLabel> guiPlayers = new ArrayList<JLabel>();

    public static String[][] playerImages = {
        {"src/images/dice/b1.png", "src/images/dice/b2.png", "src/images/dice/b3.png", "src/images/dice/b4.png", "src/images/dice/b5.png", "src/images/dice/b6.png"},
        {"src/images/dice/c1.png", "src/images/dice/c2.png", "src/images/dice/c3.png", "src/images/dice/c4.png", "src/images/dice/c5.png", "src/images/dice/c6.png"},
        {"src/images/dice/g1.png", "src/images/dice/g2.png", "src/images/dice/g3.png", "src/images/dice/g4.png", "src/images/dice/g5.png", "src/images/dice/g6.png"},
        {"src/images/dice/o1.png", "src/images/dice/o2.png", "src/images/dice/o3.png", "src/images/dice/o4.png", "src/images/dice/o5.png", "src/images/dice/o6.png"},
        {"src/images/dice/p1.png", "src/images/dice/p2.png", "src/images/dice/p3.png", "src/images/dice/p4.png", "src/images/dice/p5.png", "src/images/dice/p6.png"},
        {"src/images/dice/r1.png", "src/images/dice/r2.png", "src/images/dice/r3.png", "src/images/dice/r4.png", "src/images/dice/r5.png", "src/images/dice/r6.png"},
        {"src/images/dice/v1.png", "src/images/dice/v2.png", "src/images/dice/v3.png", "src/images/dice/v4.png", "src/images/dice/v5.png", "src/images/dice/v6.png"},
        {"src/images/dice/y1.png", "src/images/dice/y2.png", "src/images/dice/y3.png", "src/images/dice/y4.png", "src/images/dice/y5.png", "src/images/dice/y6.png"}};

    public GUI(){
        super("Deadwood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        boardPane = getLayeredPane();

        boardLabel = new JLabel();
        ImageIcon boardIcon = new ImageIcon("src/images/board.jpg");
        boardLabel.setIcon(boardIcon);
        boardLabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());

        boardPane.add(boardLabel, new Integer(0));

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

        boardPane.add(moveButton, new Integer(2));
        boardPane.add(workButton, new Integer(2));
        boardPane.add(upgradeButton, new Integer(2));

        /////////////////////////////
        /////PLAYERS/////////////////
        /////////////////////////////
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

    //move all this into a deadwood.java file when finished
    public static void main(String[] args){
        GUI gui = new GUI();
        Gamemaster game = new Gamemaster();

        gui.setVisible(true);
        game.createBoard();
        game.createCards();

        game.numberOfPlayers = askNumPlayers(gui);
        game.createPlayers();
        gui.addTurnButtons();
        game.players = gui.createGuiPlayers(game.players);
        //gui.repaint();

        

    }

    public ArrayList<Player> createGuiPlayers(ArrayList<Player> players){
        int widthOffset = 0;
        int heightOffset = 0;
        for (Player getPlayer : players) {
            JLabel playerLabel = new JLabel();
            ImageIcon pIcon = new ImageIcon(playerImages[getPlayer.playerNumber - 1][getPlayer.rank - 1]);
            playerLabel.setIcon(pIcon);
            playerLabel.setBounds(991 + widthOffset, 248 + heightOffset, pIcon.getIconWidth(), pIcon.getIconHeight());
            boardPane.add(playerLabel, new Integer(3));
            playerLabel.setVisible(true);
            widthOffset += pIcon.getIconWidth();
            if(getPlayer.playerNumber == 4){
                heightOffset += pIcon.getIconHeight();
                widthOffset = 0;
            }
            getPlayer.guiLabel = playerLabel;
        }
        return players;
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


