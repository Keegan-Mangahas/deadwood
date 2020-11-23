package deadwood;

import java.util.*;
import javax.swing.*;

public class Player {
    protected String playerColor;
    protected int playerNumber;
    protected int dollars;
    protected int credits;
    protected int rehearsalTokens;
    protected int rank;
    protected boolean onRole = false; //false by default
    protected String location;
    protected Role role;
    protected Board tempBoard;
    protected int score;
    protected boolean continueTurn = true;
    protected boolean moved = false;
    protected boolean upgraded = false;
    protected JLabel guiLabel;
    protected int widthOffSet;

    //player constructor
    public Player(int playerNum, int dol, int cred, int ran){
        playerNumber = playerNum;
        dollars = dol;
        credits = cred;
        rank = ran;
    }

    protected void resetBeforeTurn(){
        continueTurn = true;
        moved = false;
        upgraded = false;
    }

    //add reset for next turn method
    public Board playersTurn(Scanner playerInput, DeadwoodPrinter printer, Board board){
        
        resetBeforeTurn();
        tempBoard = board;
        
        while(continueTurn){
            //if player is currently working
            if(onRole){
                while(true){
                    printer.working();
                    String input = playerInput.nextLine();
                    if(input.equals("act")){
                        System.out.println();
                        act(playerInput, printer);
                        continueTurn = false;
                        break;
                    } else if(input.equals("rehearse")){
                        System.out.println();
                        rehearse(playerInput, printer);
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else if(input.equals("set")){
                        getSetRoleData(printer);
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
                        System.out.println();
                        moved = move(playerInput, printer);
                        if(moved == true){
                            movedTo(printer);
                        }
                        break;
                    } else if(input.equals("upgrade")){
                        System.out.println();
                        upgraded = upgradeRank(playerInput, printer);
                        break;
                    } else if(input.equals("work")){
                        System.out.println();
                        onRole = work(playerInput, printer);
                        if(onRole == true){
                            tempBoard.sets = updateSetRole(tempBoard.sets);
                        }
                        continueTurn = !onRole;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else if(input.equals("set")){
                        getSetRoleData(printer);
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
                        System.out.println();
                        upgraded = upgradeRank(playerInput, printer);
                        continueTurn = !upgraded;
                        break;
                    } else if(input.equals("work")){
                        System.out.println();
                        onRole = work(playerInput, printer);
                        if(onRole == true){
                            tempBoard.sets = updateSetRole(tempBoard.sets);
                        }
                        continueTurn = !onRole;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                        break;
                    } else if(input.equals("set")){
                        getSetRoleData(printer);
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
                        System.out.println();
                        moved = move(playerInput, printer);
                        if(moved == true){
                            movedTo(printer);
                        }
                        break;
                    } else if(input.equals("work")){
                        System.out.println();
                        onRole = work(playerInput, printer);
                        if(onRole == true){
                            tempBoard.sets = updateSetRole(tempBoard.sets);
                        }
                        continueTurn = !onRole;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else if(input.equals("set")){
                        getSetRoleData(printer);
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
                        System.out.println();
                        onRole = work(playerInput, printer);
                        if(onRole == true){
                            tempBoard.sets = updateSetRole(tempBoard.sets);
                        }
                        continueTurn = !onRole;
                        break;
                    } else if(input.equals("skip")){
                        continueTurn = false;
                        break;
                    } else if(input.equals("info")){
                        System.out.println(printPlayerData());
                    } else if(input.equals("set")){
                        getSetRoleData(printer);
                    } else{
                        System.out.println("Invalid input, try again");
                    }
                }
            }
        }
        return tempBoard;
    }

    private Boolean work(Scanner playerInput, DeadwoodPrinter printer){
        if("trailer".equals(location) || "office".equals(location)){
            printer.cannotWork();
            return false;
        }
        Set currentSet = new Set();
        for (Set getSet : tempBoard.sets) {
            if(location.equals(getSet.setName)){
                currentSet = getSet;
            }
        }
        if(currentSet.sceneWrapped == true){
            printer.cannotWorkWrapped();
            return false;
        }
        int totalRoles;
        while(true){
            totalRoles = printer.listRoles(currentSet);
            String input = playerInput.nextLine();
            if("back".equals(input)){
                return false;
            } else if(Integer.parseInt(input) < 1 || Integer.parseInt(input) > totalRoles){
                printer.invalid();
                continue;
            } else{
                Boolean validRole = checkRole(Integer.parseInt(input), currentSet, printer);
                if(!validRole){
                    continue;
                }
                return true;
            }
        }
    }

    private ArrayList<Set> updateSetRole(ArrayList<Set> sets){
        Set findSet = new Set();
        int setIndex = 0;
        for (Set getSet : sets) {
            if(location.equals(getSet.setName)){
                findSet = getSet;
                break;
            }
            setIndex++;
        }

        Role findRole = new Role();
        int roleIndex = 0;
        if(role.starring == false){
            for (Role getRole : findSet.roles) {
                if(role.roleName.equals(getRole.roleName)){
                    findRole = getRole;
                    break;
                }
                roleIndex++;
            }
            findSet.roles.set(roleIndex, findRole);
            sets.set(setIndex, findSet);
            return sets;
        } else {
            for (Role getRole : findSet.currentScene.roles) {
                if(role.roleName.equals(getRole.roleName)){
                    findRole = getRole;
                    break;
                }
                roleIndex++;
            }
            findSet.currentScene.roles.set(roleIndex, findRole);
            sets.set(setIndex, findSet);
            return sets;
        }
        

        
    }
    private Boolean checkRole(int roleNum, Set set, DeadwoodPrinter printer) {
        int i = 0;
        roleNum = roleNum - 1;
        for (Role role : set.roles) {
            if(roleNum == i){
                if(role.roleTaken == true){
                    printer.roleTaken();
                    return false;
                } else if(role.roleDifficulty > rank){
                    System.out.println(role.roleDifficulty);
                    System.out.println(rank);
                    printer.roleTooHard();
                    return false;
                } else if(role.roleDifficulty <= rank && role.roleTaken == false){
                    onRole = true;
                    role.roleTaken = true;
                    this.role = role;
                    printer.working(role.roleName);
                    return true;
                }
                
            }
            i++;
        }
        for (Role role : set.currentScene.roles) {
            if(roleNum == i){
                if(role.roleTaken == true){
                    printer.roleTaken();
                    return false;
                } else if(role.roleDifficulty > rank){
                    System.out.println(role.roleDifficulty);
                    System.out.println(rank);
                    printer.roleTooHard();
                    return false;
                } else if(role.roleDifficulty <= rank && role.roleTaken == false){
                    onRole = true;
                    role.roleTaken = true;
                    this.role = role;
                    printer.working(role.roleName);
                    return true;
                }
            }
            i++;
        }
        return true; //remove this
    }

    private void rehearse(Scanner playerInput, DeadwoodPrinter printer) {
        Set findSet = new Set();
        for (Set getSet : tempBoard.sets) {
            if(location.equals(getSet.setName)){
                findSet = getSet;
            }
        }
        if(rehearsalTokens + 1 == findSet.currentScene.sceneBudget ){
            printer.maxRehearseToken();
        } else {
            rehearsalTokens++;
            printer.successRehearse(rehearsalTokens);
        }
    }

    private void act(Scanner playerInput, DeadwoodPrinter printer) {
        Random die = new Random();
        printer.notifyActing();
        int dieRoll = 1 + die.nextInt(6 - 1 + 1);

        int setIndex = 0;
        Set findSet = new Set();
        for (Set getSet : tempBoard.sets) {
            if(location.equals(getSet.setName)){
                findSet = getSet;
                break;
            }
            setIndex++;
        }

        int rollTotal = dieRoll + rehearsalTokens;
        if(rollTotal >= findSet.currentScene.sceneBudget){
            printer.actingSuccess();
            findSet.takesLeft--;
            if(role.starring == true){
                credits += 2;
                printer.gotMoney(2, 0);
            } else if (role.starring == false){
                credits += 1;
                dollars += 1;
                printer.gotMoney(1, 1);
            }

            if(findSet.takesLeft == 0){
                tempBoard.sceneCardsLeft--;
                findSet.sceneWrapped = true;
                tempBoard.sets.set(setIndex, findSet);
                tempBoard.wrapThisSet = findSet;

            }
        } else {
            printer.actingFail();
            if(role.starring == true){
                printer.gotMoney(0, 0);
            } else if (role.starring == false){
                dollars += 1;
                printer.gotMoney(0, 1);
            }
        }
        
        /*
        this will need to check if scene is wrapped after acting
        since Board class seems to contain board/scene info,
        wrapScene method is in there
        */
    }

    private Boolean move(Scanner playerInput, DeadwoodPrinter printer){
        ArrayList<String> neighbors = new ArrayList<>();
        for (Set getSet : tempBoard.sets) {
            if(location.equals(getSet.setName)){
                neighbors = getSet.neighborNames;
            }
        }
        while(true){
            printer.listNeighbors(neighbors);
            String input = playerInput.nextLine();
            if(neighbors.contains(input)){
                location = input;
                printer.currentLocation(location);
                return true;
            } else if(input.equals("back")) {
                return false;
            } else{
                printer.invalid();
                continue;
            }
        }
    }
    
    protected void movedTo(DeadwoodPrinter printer) {
        Set currentSet = new Set();
        int setIndex = 0;
        for (Set getSet : tempBoard.sets) {
            if(location.equals(getSet.setName)){
                currentSet = getSet;  
                if(currentSet.sceneDiscovered == false && !"trailer".equals(currentSet.setName) && !"office".equals(currentSet.setName)){
                    currentSet.sceneDiscovered = true;
                    printer.discoveredScene(currentSet);
                    tempBoard.sets.set(setIndex, currentSet);
                } else if(currentSet.sceneWrapped == true){
                    printer.thisSetIsWrapped();
                }
                break;
            }
            setIndex++;
        }
    }
    

    private void getSetRoleData(DeadwoodPrinter printer){
        if("trailer".equals(location)){
            printer.printTrailerData();
            return;
        } else if ("office".equals(location)){
            printer.printOfficeData();
            return;
        }
        Set currentSet = new Set();
        for (Set getSet : tempBoard.sets) {
            if(location.equals(getSet.setName)){
                currentSet = getSet;  
                currentSet.sceneDiscovered = true;
                printer.printSetRoleData(currentSet);
                
                break;
            }
        }
    }

    protected boolean upgradeRank(int rank, String currency){
        switch(rank){
            case 2:
                return upgradeP2(currency, rank, 4, 5);
            case 3:
                return upgradeP2(currency, rank, 10, 10);
            case 4:
                return upgradeP2(currency, rank, 18, 15);
            case 5:
                return upgradeP2(currency, rank, 28, 20);
            case 6:
                return upgradeP2(currency, rank, 40, 25);
        }
        return false;
    }

    protected boolean upgradeP2(String currency, int newRank, int dolPrice, int credPrice){
        switch(currency){
            case "credits":
                if(credits >= credPrice){
                    this.credits -= credPrice;
                    this.rank = newRank;
                    return true;
                } else {
                    return false;
                }
            case "dollars":
                if(dollars >= dolPrice){
                    this.dollars -= dolPrice;
                    this.rank = newRank;
                    return true;
                } else {
                    return false;
            }
        }
        return false;
    }

    private boolean upgradeRank(Scanner playerInput, DeadwoodPrinter printer) {
        //THIS NEEDS TO CHECK IF PLAYER IS IN UPGRADE ROOM STILL
        //REPLACE IF STATEMENT
        if("office".equals(location)){
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
        } else {
            printer.notInOffice();
            return false;
        }
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
                ", Location: "+location;
                //print player location too
    }
}
