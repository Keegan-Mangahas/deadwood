package deadwood;

import java.util.*;

public class Player {
    protected int playerNumber;
    protected int dollars;
    protected int credits;
    protected int rehearsalTokens;
    protected int rank;
    protected boolean onRole = false; //false by default
    protected String location;

    //player constructor
    public Player(int playerNum, int dol, int cred, int ran){
        playerNumber = playerNum;
        dollars = dol;
        credits = cred;
        rank = ran;
    }

    private Set getCurrentRoom() {
        //find where player currently is
        //do we want to store this as its own variable?
        //or is it easier for Room class to store players in room?
        return null;
    }

    public String playersTurn(Scanner playerInput, DeadwoodPrinter printer){
        /*
        moveTo()
        upgradeRank()
        rehearse()
        chooseRole()
        act()
        
        make it so player can continue turn if applicable
        ex. player moves, they can upgrade OR take role then work
        */
        boolean continueTurn = true;
        boolean moved = false;
        boolean upgraded = false;
        while(continueTurn){
            //if player is currently working
            if(onRole){
                while(true){
                    printer.working();
                    String input = playerInput.nextLine();
                    if(input.equals("act")){
                        System.out.println("ACT ROLE");
                        continueTurn = false;
                        break;
                    } else if(input.equals("rehearse")){
                        System.out.println("REHEARSE ROLE");
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else{
                        System.out.println("Invalid input, try again");
                    }
                }
                //if player isn't working and hasn't done anything
            } else if(!moved && !upgraded){
                while(true){
                    printer.notMoveNotUpgrade();
                    String input = playerInput.nextLine();
                    if(input.equals("move")){
                        System.out.println("MOVE PLAYER");
                        moved = true; //MOVE THIS INTO MOVE METHOD INCASE PLAYER CHANGES MIND
                        break;
                    } else if(input.equals("upgrade")){
                        System.out.println("UPGRADE RANK");
                        upgraded = upgradeRank(playerInput, printer);
                        break;
                    } else if(input.equals("work")){
                        System.out.println("START WORK ON ROLE");
                        onRole = true;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else{
                        System.out.println("Invalid input, try again");
                    }
                }
                //if player isn't working but moved already
            } else if(moved && !upgraded){
                while(true){
                    printer.moveNotUpgrade();
                    String input = playerInput.nextLine();
                    if(input.equals("upgrade")){
                        System.out.println("UPGRADE RANK");
                        upgraded = upgradeRank(playerInput, printer);
                        continueTurn = !upgraded;
                        break;
                    } else if(input.equals("work")){
                        System.out.println("START WORK ON ROLE");
                        onRole = true;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                        break;
                    } else{
                        System.out.println("Invalid input, try again");
                    }
                }
                //if player isn't working but upgraded already
            } else if(!moved && upgraded){
                while(true){
                    printer.notMoveUpgrade();
                    String input = playerInput.nextLine();
                    if(input.equals("move")){
                      System.out.println("MOVE PLAYER");
                        moved = true; //MOVE THIS INTO MOVE METHOD INCASE PLAYER CHANGES MIND
                        break;
                    } else if(input.equals("work")){
                        System.out.println("START WORK ON ROLE");
                        onRole = true;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else{
                        System.out.println("Invalid input, try again");
                    }
                }
                //if player upgraded, then moved
            } else if(moved && upgraded){
                while(true){
                    printer.moveUpgrade();
                    String input = playerInput.nextLine();
                    if(input.equals("work")){
                        System.out.println("START WORK ON ROLE");
                        onRole = true;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else{
                        System.out.println("Invalid input, try again");
                    }
                }
            }
        }
        return "next";
    }

    private void moveTo(Set destRoom) {
        /*
        this will need to check for valid rooms user can move into
        will also probably trigger stuff for undiscovered scenes
        */
    }

    private boolean upgradeRank(Scanner playerInput, DeadwoodPrinter printer) {
        //THIS NEEDS TO CHECK IF PLAYER IS IN UPGRADE ROOM STILL
        //REPLACE IF STATEMENT
        if(true){
            while(true){
                printer.ranksList();
                printer.askRank();
                String input = playerInput.nextLine();
                switch(input){
                    case "back":
                        return false;
                    case "2":
                        if(Integer.parseInt(input) <= rank){
                            printer.invalidRank(input);
                            continue;
                        }
                        return upgradeP2(playerInput, printer, input, 4, 5);
                    case "3":
                        if(Integer.parseInt(input) <= rank){
                            printer.invalidRank(input);
                            continue;
                        }
                        return upgradeP2(playerInput, printer, input, 10, 10);
                    case "4":
                        if(Integer.parseInt(input) <= rank){
                            printer.invalidRank(input);
                            continue;
                        }
                        return upgradeP2(playerInput, printer, input, 18, 15);
                        
                    case "5":
                        if(Integer.parseInt(input) <= rank){
                            printer.invalidRank(input);
                            continue;
                        }
                        return upgradeP2(playerInput, printer, input, 28, 20);
                    case "6":
                        if(Integer.parseInt(input) <= rank){
                            printer.invalidRank(input);
                            continue;
                        }
                        return upgradeP2(playerInput, printer, input, 40, 25);
                    default:
                        printer.invalid();
                        continue;
                }
            }
        }
        return false; //base case???
    }

    private boolean upgradeP2(Scanner playerInput, DeadwoodPrinter printer, String input, int dolPrice, int credPrice){
        while(true){
            printer.payment();
            String payment = playerInput.nextLine();
            switch(payment){
                case "credits":
                    if(credits >= Integer.valueOf(input)){
                        credits -= credPrice;
                        rank = Integer.valueOf(input);
                        printer.rankSuccess(input);
                        return true;
                    } else {
                        printer.rankFail(payment);
                        return false;
                    }
                case "dollars":
                    if(dollars >= Integer.valueOf(input)){
                        dollars -= dolPrice;
                        rank = Integer.valueOf(input);
                        printer.rankSuccess(input);
                        return true;
                    } else {
                        printer.rankFail(payment);
                        return false;
                }
                default:
                    printer.invalid();
                    continue;
            }
        }
        //remove this
    }

    private void rehearse() {
        //give player rehearse counter
        //force to act if rehearse counters are max
    }

    private void act() {
        /*
        this will need to check if scene is wrapped after acting
        since Board class seems to contain board/scene info,
        wrapScene method is in there
        */
    }

    private void chooseRole() {
        //will need to make sure player can take role
        //will also let player work on role if they take it
    }

    public boolean isOnRole() {
        return onRole;
    }

    private void setDollars(int amount) {
        this.dollars = amount;
    }

    private void setCredits(int amount) {
        this.credits = amount;
    }

    private void setRank(int amount){
        this.rank = amount;
    }

    public int getDollars() {
        return dollars;
    }

    public int getCredits() {
        return credits;
    }

    public int getRank(){
        return rank;
    }

    public String printPlayerData(){
        return "Player "+playerNumber+
                " - Credits: "+credits+
                ", Dollars: "+dollars+
                ", Rank: "+rank+
                ", Rehearsal Tokens: "+rehearsalTokens+
                ", Location: NOT IMPLEMENTED";
                //print player location too
    }
}
