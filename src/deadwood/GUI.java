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
    JLabel cardLabel;

    JLabel numberOfDays = new JLabel();
    JLabel sceneCardsLeft = new JLabel();
    JLabel currentPlayer = new JLabel();
    JLabel playerRank = new JLabel();
    JLabel playerCredits = new JLabel();
    JLabel playerDollars = new JLabel();
    JLabel playerRehearseTokens = new JLabel();
    JLabel playerLocation = new JLabel();
    //buttons
    JButton moveButton;
    JButton workButton;
    JButton upgradeButton;
    JButton skipButton;
    JButton backButton;
    JButton locationButton;

    JLabel playerLabel;

    public Boolean moveClicked = false;

    public static ArrayList<JLabel> guiPlayers = new ArrayList<JLabel>();
    public static ArrayList<JButton> locationButtons = new ArrayList<JButton>();

    public static String[] playerColors = {"Blue", "Cyan", "Green", "Orange", "Pink", "Red", "Violet", "Yellow"};

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
        moveButton.setBounds(1200 + 10, 30, 100, 20);
        moveButton.addMouseListener(new boardMouseListener());
        moveButton.setVisible(false);

        workButton = new JButton("WORK");
        workButton.setBackground(Color.white);
        workButton.setBounds(1200 + 10, 60, 100, 20);
        workButton.addMouseListener(new boardMouseListener());
        workButton.setVisible(false);

        upgradeButton = new JButton("UPGRADE");
        upgradeButton.setBackground(Color.white);
        upgradeButton.setBounds(1200 + 10, 90, 100, 20);
        upgradeButton.addMouseListener(new boardMouseListener());
        upgradeButton.setVisible(false);

        skipButton = new JButton("SKIP");
        skipButton.setBackground(Color.white);
        skipButton.setBounds(1200 + 10, 120, 100, 20);
        skipButton.addMouseListener(new boardMouseListener());
        skipButton.setVisible(false);

        backButton = new JButton("BACK");
        backButton.setBackground(Color.white);
        backButton.setBounds(1200 + 10, 90, 100, 20);
        backButton.addMouseListener(new boardMouseListener());
        backButton.setVisible(false);

        boardPane.add(backButton, new Integer(2));
        boardPane.add(moveButton, new Integer(2));
        boardPane.add(workButton, new Integer(2));
        boardPane.add(skipButton, new Integer(2));
        boardPane.add(upgradeButton, new Integer(2));

    }

    public void addAllTurnButtons(){
        this.moveButton.setVisible(true);
        this.workButton.setVisible(true);
        this.upgradeButton.setVisible(true);
        this.skipButton.setVisible(true);
    }

    public void removeAllTurnButtons(){
        this.moveButton.setVisible(false);
        this.workButton.setVisible(false);
        this.upgradeButton.setVisible(false);
        this.skipButton.setVisible(false);
    }

    public void addSelectedTurnButtons(int choice){
        if(choice == 0){
            //TODO: make act and rehearse buttons visible only
        } else if(choice == 1){
            this.moveButton.setVisible(true);
            this.workButton.setVisible(true);
            this.upgradeButton.setVisible(true);
            this.skipButton.setVisible(true);
        } else if(choice == 2){
            this.moveButton.setVisible(false);
            this.workButton.setVisible(true);
            this.upgradeButton.setVisible(true);
            this.skipButton.setVisible(true);
        } else if(choice == 3){
            this.moveButton.setVisible(true);
            this.workButton.setVisible(true);
            this.upgradeButton.setVisible(false);
            this.skipButton.setVisible(true);
        } else if(choice == 4){
            this.moveButton.setVisible(false);
            this.workButton.setVisible(true);
            this.upgradeButton.setVisible(false);
            this.skipButton.setVisible(true);
        }
    }

    class boardMouseListener implements MouseListener{
        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == moveButton){
                Deadwood.movePlayer();
            } else if (e.getSource() == workButton){
                System.out.println("WORK PLAYER");
            } else if (e.getSource() == upgradeButton){
                System.out.println("UPGRADE PLAYER");
            } else if (e.getSource() == locationButton){
                System.out.println("MOVE TO LOCATION");
            }  else if (e.getSource() == skipButton){
                System.out.println("SKIPPING TURN");
                Deadwood.endTurn();
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

    public void runTurn(Player currentPlayer){
        displayInfo(currentPlayer);
        if(currentPlayer.continueTurn == true){
            if(currentPlayer.onRole == true){
                System.out.println("SHOW ACT REHEARSE BUTTONS");
            } else if(!currentPlayer.moved && !currentPlayer.upgraded){
                System.out.println("MOVE UPGRADE WORK SKIP BUTTONS");
                addSelectedTurnButtons(1);
            } else if(currentPlayer.moved && !currentPlayer.upgraded){
                System.out.println("UPGRADE WORK SKIP BUTTONS");
                addSelectedTurnButtons(2);
            } else if(!currentPlayer.moved && currentPlayer.upgraded){
                System.out.println("MOVE WORK SKIP BUTTONS");
                addSelectedTurnButtons(3);
            } else if(currentPlayer.moved && currentPlayer.upgraded){
                System.out.println("WORK SKIP BUTTONS");
                addSelectedTurnButtons(4);
            }
        }
    }

    public void displayInfo(Player player){
        numberOfDays.setText("Days left: " + player.tempBoard.maxGameDays);
        numberOfDays.setBounds(1200 + 10, 500, 300, 20);
        boardPane.add(numberOfDays, new Integer(2));

        sceneCardsLeft.setText("Scenes left: " + player.tempBoard.sceneCardsLeft);
        sceneCardsLeft.setBounds(1200 + 10, 520, 300, 20);
        boardPane.add(sceneCardsLeft, new Integer(2));

        currentPlayer.setText("Current player: " + player.playerColor);
        currentPlayer.setBounds(1200 + 10, 540, 300, 20);
        boardPane.add(currentPlayer, new Integer(2));

        playerRank.setText("Rank: " + player.rank);
        playerRank.setBounds(1200 + 10, 560, 300, 20);
        boardPane.add(playerRank, new Integer(2));

        playerCredits.setText("Credits: " + player.credits);
        playerCredits.setBounds(1200 + 10, 580, 300, 20);
        boardPane.add(playerCredits, new Integer(2));

        playerDollars.setText("Dollars: " + player.dollars);
        playerDollars.setBounds(1200 + 10, 600, 300, 20);
        boardPane.add(playerDollars, new Integer(2));

        playerRehearseTokens.setText("Rehearse Tokens: " + player.rehearsalTokens);
        playerRehearseTokens.setBounds(1200 + 10, 620, 300, 20);
        boardPane.add(playerRehearseTokens, new Integer(2));

        playerLocation.setText("Location: " + player.location);
        playerLocation.setBounds(1200 + 10, 640, 300, 20);
        boardPane.add(playerLocation, new Integer(2));
    }

    public void updatePlayerLocation(Player currentPlayer, ArrayList<Set> sets){
        Set currentSet = new Set();
        for (Set set : sets) {
            if(currentPlayer.location.equals(set.setName)){
                currentSet = set;
            }
        }
        currentPlayer.guiLabel.setBounds(Integer.parseInt(currentSet.x) + currentPlayer.widthOffSet , Integer.parseInt(currentSet.y) + 50, Integer.parseInt(currentSet.w), Integer.parseInt(currentSet.h));

        //runTurn(currentPlayer); this is called in Deadwood.movePlayerHere
    }

    
    public void revealSceneCard(Player currentPlayer, ArrayList<Set> sets){
        Set currentSet = new Set();
        for (Set set : sets) {
            if(currentPlayer.location.equals(set.setName)){
                currentSet = set;
            }
        }
        if(!"office".equals(currentSet.setName) && !"trailer".equals(currentSet.setName)){
            JLabel cardLabel = currentSet.cardBackLabel;
            boardPane.remove(cardLabel);
            boardPane.revalidate();
            boardPane.repaint();
        }
    }

    public void addMoveOptions(Gamemaster game){
        removeAllTurnButtons();
        int heightOffset = 0;
        Set playerLocationSet = new Set();
        ArrayList<String> neighbors = new ArrayList<String>();
        for (Set getSet : Gamemaster.board.sets) {
            if(game.currentPlayer.location.equals(getSet.setName)){
                neighbors = getSet.neighborNames;
                playerLocationSet = getSet;
            }
        }
        int index = 0;
        for (String string : neighbors) {
            locationButtons.add(new JButton(string));
            locationButtons.get(index).setBackground(Color.white);
            locationButtons.get(index).setBounds(1200 + 10, 30 + heightOffset, 100, 20);
            locationButtons.get(index).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String choice = e.getActionCommand();
                    System.out.println("You have picked " + choice);
                    Deadwood.movePlayerHere(choice);
                    for (JButton button : locationButtons) {
                        button.setVisible(false);
                    }
                    
                    locationButtons.clear();
                    backButton.setVisible(false);
                }
            });
            boardPane.add(locationButtons.get(index), 2);
            locationButtons.get(index).setVisible(true);

            heightOffset += 30;
            index++;
        }
        backButton.setBounds(1200 + 10, 30 + heightOffset, 100, 20);
        backButton.setVisible(true);
    }


    public void addGUISceneCards(ArrayList<Set> sets){
        for (Set set : sets) {
            if(!"trailer".equals(set.setName) && !"office".equals(set.setName)){
                Scene setScene = set.currentScene; //TODO: Not needed?
                placeSceneCard(set);
                placeSceneCardBack(set);
            }
        }
    }

    public void placeSceneCard(Set set){
        cardLabel = new JLabel();
        ImageIcon cardIcon = new ImageIcon("src/images/cards/" + set.currentScene.sceneImage);
        cardLabel.setIcon(cardIcon);
        cardLabel.setBounds(Integer.parseInt(set.x), Integer.parseInt(set.y), Integer.parseInt(set.w), Integer.parseInt(set.h));
        set.cardLabel = cardLabel;
        boardPane.add(cardLabel, new Integer(2));
    }

    public void placeSceneCardBack(Set set){
        cardLabel = new JLabel();
        ImageIcon cardBackIcon = new ImageIcon("src/images/CardBack-small.jpg");
        cardLabel.setIcon(cardBackIcon);
        cardLabel.setBounds(Integer.parseInt(set.x), Integer.parseInt(set.y), Integer.parseInt(set.w), Integer.parseInt(set.h));
        set.cardBackLabel = cardLabel;
        boardPane.add(cardLabel, new Integer(3));
    }

    //create the each player on the GUI and return players arraylist now that each player has a label
    public ArrayList<Player> createGuiPlayers(ArrayList<Player> players){
        int playerWidthOffset = 0;
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
            getPlayer.widthOffSet =  playerWidthOffset;
            getPlayer.playerColor = playerColors[getPlayer.playerNumber - 1];
            playerWidthOffset += pIcon.getIconWidth()/2;
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


