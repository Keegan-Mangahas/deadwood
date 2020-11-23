package deadwood;

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.*;

public class GUI extends JFrame{
    //pane
    JLayeredPane boardPane;
    //labels
    JLabel boardLabel;
    JLabel menuLabel;
    JLabel cardLabel;
    JLabel playerLabel;
    JLabel locationLabel;
    JLabel upgradeLabel;
    JLabel currencyLabel;
    JLabel roleLabel;

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
    JButton actButton;
    JButton rehearseButton;
    JButton skipButton;
    JButton backButton;
    JButton locationButton;
    JButton upgradeOptionButton;
    JButton dollarButton;
    JButton creditButton;
    JButton roleButton;

    public static ArrayList<JLabel> guiPlayers = new ArrayList<JLabel>();
    public static ArrayList<JButton> locationButtons = new ArrayList<JButton>();
    public static ArrayList<JButton> upgradeButtons = new ArrayList<JButton>();
    public static ArrayList<JButton> roleButtons = new ArrayList<JButton>();

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

        menuLabel = new JLabel("MENU");
        menuLabel.setBounds(boardIcon.getIconWidth() + 40 , 0, 100, 20);
        boardPane.add(menuLabel, 2);

        locationLabel = new JLabel("LOCATIONS");
        locationLabel.setBounds(1200 + 40 , 0, 100, 20);
        boardPane.add(locationLabel, 2);
        locationLabel.setVisible(false);

        upgradeLabel = new JLabel("UPGRADES");
        upgradeLabel.setBounds(1200 + 40 , 0, 100, 20);
        boardPane.add(upgradeLabel, 2);
        upgradeLabel.setVisible(false);

        currencyLabel = new JLabel("PURCHASE WITH");
        currencyLabel.setBounds(1200 + 40 , 0, 100, 20);
        boardPane.add(currencyLabel, 2);
        currencyLabel.setVisible(false);

        roleLabel = new JLabel("PICK ROLE");
        roleLabel.setBounds(1200 + 40, 0, 100, 20);
        boardPane.add(roleLabel, 2);
        roleLabel.setVisible(false);
        
        
        /////////////////////////////
        /////BUTTONS/////////////////
        /////////////////////////////


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

        actButton = new JButton("ACT");
        actButton.setBackground(Color.white);
        actButton.setBounds(1200 + 10, 30, 100, 20);
        actButton.addMouseListener(new boardMouseListener());
        actButton.setVisible(false);

        rehearseButton = new JButton("REHEARSE");
        rehearseButton.setBackground(Color.white);
        rehearseButton.setBounds(1200 + 10, 60, 100, 20);
        rehearseButton.addMouseListener(new boardMouseListener());
        rehearseButton.setVisible(false);

        skipButton = new JButton("SKIP");
        skipButton.setBackground(Color.white);
        skipButton.setBounds(1200 + 10, 120, 100, 20);
        skipButton.addMouseListener(new boardMouseListener());
        skipButton.setVisible(false);

        backButton = new JButton("BACK");
        backButton.setBackground(Color.white);
        backButton.addMouseListener(new boardMouseListener());
        backButton.setVisible(false);

        creditButton = new JButton("Credits");
        creditButton.setBackground(Color.white);
        creditButton.setBounds(1200 + 10, 30, 100, 20);
        backButton.setVisible(false);

        dollarButton = new JButton("Dollars");
        dollarButton.setBackground(Color.white);
        dollarButton.setBounds(1200 + 10, 60, 100, 20);
        backButton.setVisible(false);

        boardPane.add(backButton, new Integer(2));
        boardPane.add(moveButton, new Integer(2));
        boardPane.add(workButton, new Integer(2));
        boardPane.add(actButton, new Integer(2));
        boardPane.add(rehearseButton, new Integer(2));
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
        this.menuLabel.setVisible(false);
        this.moveButton.setVisible(false);
        this.workButton.setVisible(false);
        this.upgradeButton.setVisible(false);
        this.actButton.setVisible(false);
        this.rehearseButton.setVisible(false);
        this.skipButton.setVisible(false);
    }
    public void removeAllExtraLabels(){
        this.upgradeLabel.setVisible(false);
        this.locationLabel.setVisible(false);
        this.currencyLabel.setVisible(false);
    }

    public void addSelectedTurnButtons(int choice){
        this.menuLabel.setVisible(true);
        if(choice == 0){
            removeAllTurnButtons();
            this.menuLabel.setVisible(true);
            this.actButton.setVisible(true);
            this.rehearseButton.setVisible(true);
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
                Deadwood.workPlayer();
            } else if (e.getSource() == upgradeButton){
                System.out.println("UPGRADE PLAYER");
                Deadwood.upgradePlayer();
            } else if(e.getSource() == actButton){
                System.out.println("ACTING");
                Deadwood.act();
            } else if(e.getSource() == rehearseButton){
                System.out.println("REHEARSING");
                Deadwood.rehearse();
            } else if (e.getSource() == locationButton){
                System.out.println("MOVE TO LOCATION");
            } else if (e.getSource() == backButton){
                System.out.println("GOING BACK");
                removeAllExtraLabels();
                for (JButton button : upgradeButtons) {
                    button.setVisible(false);
                }
                upgradeLabel.setVisible(false);
                upgradeButtons.clear();

                for (JButton button : locationButtons) {
                    button.setVisible(false);
                }
                locationLabel.setVisible(false);
                locationButtons.clear();

                creditButton.setVisible(false);
                dollarButton.setVisible(false);
                for(ActionListener act : creditButton.getActionListeners()){
                    creditButton.removeActionListener(act);
                }
                for(ActionListener act : dollarButton.getActionListeners()){
                    dollarButton.removeActionListener(act);
                }

                for (JButton button : roleButtons) {
                    button.setVisible(false);
                }
                roleLabel.setVisible(false);
                roleButtons.clear();

                backButton.setVisible(false);
                Deadwood.back();
            } else if (e.getSource() == skipButton){
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
                addSelectedTurnButtons(0);
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

    public void showNextPlayersTurn(){
        JOptionPane.showMessageDialog(null, "Is is the next players turn");
    }

    public void showInvalid(){
        JOptionPane.showMessageDialog(null, "You cannot do that");
    }

    public void showInvalidRehearse(){
        JOptionPane.showMessageDialog(null, "You have too many rehearsal tokens, you must act");
    }

    public void showRehearseSuccess(){
        JOptionPane.showMessageDialog(null, "You rehearsed your roll!");
    }

    public void putPlayerOnRole(Player currentPlayer, ArrayList<Set> sets){
        Set currentSet = new Set();
        for (Set set : sets) {
            if(currentPlayer.location.equals(set.setName)){
                currentSet = set;
            }
        }
        if(currentPlayer.role.starring == false){
            currentPlayer.guiLabel.setBounds(Integer.parseInt(currentPlayer.role.x), Integer.parseInt(currentPlayer.role.y), Integer.parseInt(currentPlayer.role.w), Integer.parseInt(currentPlayer.role.h));
        } else {
            currentPlayer.guiLabel.setBounds(Integer.parseInt(currentSet.x) + Integer.parseInt(currentPlayer.role.x), Integer.parseInt(currentSet.y) + 10, Integer.parseInt(currentSet.w), Integer.parseInt(currentSet.h));
        }
        
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

    public void showRoleOptions(Set set){
        removeAllTurnButtons();
        roleLabel.setVisible(true);
        int heightOffset = 0;

        int index = 0;
        int i = 0;
        for (Role role : set.roles) {
            roleButtons.add(new JButton(role.roleName));
            roleButtons.get(index).setBackground(Color.white);
            roleButtons.get(index).setBounds(1200 + 10, 30 + heightOffset, 150, 20);
            roleButtons.get(index).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String choice = e.getActionCommand();
                    System.out.println("You have picked " + choice);
                    for (JButton button : roleButtons) {
                        button.setVisible(false);
                    }
                    roleLabel.setVisible(false);
                    roleButtons.clear();
                    backButton.setVisible(false);
                    Deadwood.addToRole(choice);
                }
            });
            boardPane.add(roleButtons.get(index), new Integer(2));
            roleButtons.get(index).setVisible(true);
            heightOffset += 30;
            index++;
        }
        for (Role role : set.currentScene.roles) {
            roleButtons.add(new JButton(role.roleName));
            roleButtons.get(index).setBackground(Color.white);
            roleButtons.get(index).setBounds(1200 + 10, 30 + heightOffset, 150, 20);
            roleButtons.get(index).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String choice = e.getActionCommand();
                    System.out.println("You have picked " + choice);
                    for (JButton button : roleButtons) {
                        button.setVisible(false);
                    }
                    roleLabel.setVisible(false);
                    roleButtons.clear();
                    backButton.setVisible(false);
                    Deadwood.addToRole(choice);
                }
            });
            boardPane.add(roleButtons.get(index), new Integer(2));
            roleButtons.get(index).setVisible(true);
            heightOffset += 30;
            index++;
        }
        backButton.setBounds(1200 + 10, 30 + heightOffset, 100, 20);
        backButton.setVisible(true);
    } 

    public void addUpgradeOptions(Gamemaster game){
        removeAllTurnButtons();
        upgradeLabel.setVisible(true);
        int heightOffset = 0;

        int index = 0;
        for(int i = game.currentPlayer.rank + 1; i <= 6; i++){
            upgradeButtons.add(new JButton(Integer.toString(i)));
            upgradeButtons.get(index).setBackground(Color.white);
            upgradeButtons.get(index).setBounds(1200 + 10, 30 + heightOffset, 100, 20);
            upgradeButtons.get(index).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int choice = Integer.parseInt(e.getActionCommand());
                    System.out.println("You have picked " + choice);
                    for (JButton button : upgradeButtons) {
                        button.setVisible(false);
                    }
                    upgradeLabel.setVisible(false);
                    upgradeButtons.clear();
                    backButton.setVisible(false);
                    addCurrencyOptions(choice);
                }
            });
            boardPane.add(upgradeButtons.get(index), new Integer(2));
            upgradeButtons.get(index).setVisible(true);
            heightOffset += 30;
            index++;
        }
        backButton.setBounds(1200 + 10, 30 + heightOffset, 100, 20);
        backButton.setVisible(true);
    }

    public void addCurrencyOptions(int rankChoice){
        currencyLabel.setVisible(true);
        creditButton.setVisible(true);
        creditButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("You have picked credits");
                for(ActionListener act : creditButton.getActionListeners()){
                    creditButton.removeActionListener(act);
                }
                for(ActionListener act : dollarButton.getActionListeners()){
                    dollarButton.removeActionListener(act);
                }
                creditButton.setVisible(false);
                dollarButton.setVisible(false);
                currencyLabel.setVisible(false);
                backButton.setVisible(false);
                Deadwood.upgradePlayerRank(rankChoice, "credits");
            }
        });
        boardPane.add(creditButton, new Integer(2));

        dollarButton.setVisible(true);
        dollarButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("You have picked dollars");
                for(ActionListener act : creditButton.getActionListeners()){
                    creditButton.removeActionListener(act);
                }
                for(ActionListener act : dollarButton.getActionListeners()){
                    dollarButton.removeActionListener(act);
                }
                dollarButton.setVisible(false);
                creditButton.setVisible(false);
                currencyLabel.setVisible(false);
                backButton.setVisible(false);
                Deadwood.upgradePlayerRank(rankChoice, "dollars");
            }
        });
        boardPane.add(dollarButton, new Integer(2));

        backButton.setBounds(1200 + 10, 90, 100, 20);
        backButton.setVisible(true);
    }

    public void changePlayerRankGUI(Player currentPlayer){
        ImageIcon newRankIcon = new ImageIcon(playerImages[currentPlayer.playerNumber - 1][currentPlayer.rank - 1]);
        currentPlayer.guiLabel.setIcon(newRankIcon);
        boardPane.revalidate();
        boardPane.repaint();
    }

    public void insufficent(){
        JOptionPane.showMessageDialog(null, "Upgrade failed, not enough money");
    }

    public void addMoveOptions(Gamemaster game){
        removeAllTurnButtons();
        locationLabel.setVisible(true);
        int heightOffset = 0;
        ArrayList<String> neighbors = new ArrayList<String>();
        for (Set getSet : Gamemaster.board.sets) {
            if(game.currentPlayer.location.equals(getSet.setName)){
                neighbors = getSet.neighborNames;
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
                    locationLabel.setVisible(false);
                    locationButtons.clear();
                    backButton.setVisible(false);
                }
            });
            boardPane.add(locationButtons.get(index), new Integer(2));
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
            boardPane.add(playerLabel, new Integer(5));
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


