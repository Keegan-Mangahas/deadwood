package deadwood;

import java.util.*;

public class DeadwoodPrinter extends Gamemaster{
    protected void askPlayers(){
        System.out.println("How many players? (2-8)");
    }
    protected void invalidPlayers(){
        System.out.println("Please enter a valid number of players");
    }
    protected void whoseTurn(Player player){
        System.out.println("It is Player "+player.playerNumber+"'s turn");
    }
    protected void working(){
        System.out.println("Would you like to [act] or [rehearse]? Or player [info], [set] info");
    }
    protected void notMoveNotUpgrade(){
        System.out.println("Would you like to [move], [upgrade], [work], [skip]? Or player [info], [set] info");
    }
    protected void moveNotUpgrade(){
        System.out.println("Would you like to [upgrade], [work], [skip]? Or player [info], [set] info");
    }
    protected void notMoveUpgrade(){
        System.out.println("Would you like to [move], [work], [skip]? Or player [info], [set] info");
    }
    protected void moveUpgrade(){
        System.out.println("Would you like to [work], [skip]? Or player [info], [set] info");
    }
    protected void ranksList(){
        System.out.printf("%nRank 2: 4 Dollars OR 5 Credits%nRank 3: 10 Dollars OR 10 Credits%nRank 4: 18 Dollars OR 15 Credits%nRank 5: 28 Dollars OR 20 Credits%nRank 6: 40 Dollars OR 25 Credits%n");
    }
    protected void askRank(){
        System.out.printf("%nWhich rank would you like to upgrade to? [2], [3]...[6]. Or go [back]%n");
    }
    protected void invalidRank(String input){
        System.out.println("You cannot go to rank "+input+", try again");
    }
    protected void invalid(){
        System.out.println("Invalid entry, try again");
    }
    protected void payment(){
        System.out.println("Would you like to pay with [credits] or [dollars]?");
    }
    protected void rankSuccess(String input){
        System.out.println("You are now rank " + input);
        System.out.println();
    }
    protected void rankFail(String type){
        System.out.println("Rank not upgraded, not enough " + type);
        System.out.println();
    }
    protected void notInOffice(){
        System.out.println("You may not upgrade, you are not in the office");
    }
    protected void listNeighbors(ArrayList<String> neighbors){
        System.out.print("Where would you like to move? ");
        for (String string : neighbors) {
            System.out.printf("[%s] ", string);
        }
        System.out.print(" Or [back]");
        System.out.println();
    }
    protected void currentLocation(String string){
        System.out.println("You are now in "+string);
        System.out.println();
    }
    protected void discoveredScene(Set set){
        System.out.println("You found a new scene! Scene info:");
        System.out.printf("Scene name: %s, Scene Budget: %d%nScene Desc: %s%n", set.currentScene.sceneName, set.currentScene.sceneBudget, set.currentScene.sceneDescription);
        for (Role role : set.roles) {
            System.out.printf("    Role name: %s, Difficulty: %d, Starring: %b%n        Line: %s%n", role.roleName, role.roleDifficulty, role.starring, role.roleDescription);
        }
        for (Role role : set.currentScene.roles) {
            System.out.printf("    Role name: %s, Difficulty: %d, Starring: %b%n        Line: %s%n", role.roleName, role.roleDifficulty, role.starring, role.roleDescription);
        }
    }
    protected void printSetRoleData(Set set){
        System.out.printf("Scene name: %s, Scene Budget: %d%nScene Desc: %s%n", set.currentScene.sceneName, set.currentScene.sceneBudget, set.currentScene.sceneDescription);
        for (Role role : set.roles) {
            System.out.printf("    Role name: %s, Difficulty: %d, Starring: %b%n        Line: %s%n", role.roleName, role.roleDifficulty, role.starring, role.roleDescription);
        }
        for (Role role : set.currentScene.roles) {
            System.out.printf("    Role name: %s, Difficulty: %d, Starring: %b%n        Line: %s%n", role.roleName, role.roleDifficulty, role.starring, role.roleDescription);
        }
    }
    protected void printTrailerData(){
        System.out.println("You're in the trailer, not much going on here...");
    }
    protected void printOfficeData(){
        System.out.println("You're in the office, not much going on here...");
    }
    protected void thisSetIsWrapped(){
        System.out.println("This set is wrapped");
    }
    protected int listRoles(Set set){
        System.out.println("Which role would you like:");
        int i = 0;
        for (Role role : set.roles) {
            System.out.printf("[%d]    Role name: %s, Difficulty: %d, Starring: %b%n        Line: %s%n", i+1, role.roleName, role.roleDifficulty, role.starring, role.roleDescription);
            i++;
        }

        for (Role role : set.currentScene.roles) {
            System.out.printf("[%d]    Role name: %s, Difficulty: %d, Starring: %b%n        Line: %s%n", i+1, role.roleName, role.roleDifficulty, role.starring, role.roleDescription);
            i++;
        }
        System.out.println("Or [back]");
        return i;
    }
    protected void cannotWork(){
        System.out.println("You cannot work here...");
    }
    protected void roleTaken(){
        System.out.println("This role is already taken");
    }
    protected void roleTooHard(){
        System.out.println("This role is too difficult for you");
    }
    protected void working(String string){
        System.out.println("You are now working the role " + string);
    }
    protected void maxRehearseToken(){
        System.out.printf("You have the maximum amount of rehearsal tokens for this scene%nYou must act%n");
    }
    protected void successRehearse(int tokens){
        System.out.printf("You successful rehearsed! You now have %d rehearsal tokens!%n", tokens);
    }
    protected void notifyActing(){
        System.out.println("You are acting!");
    }
    protected void actingSuccess(){
        System.out.println("You sucessfully acted!");
    }
    protected void gotMoney(int cred, int dol){
        System.out.printf("You gained %d credits and %d dollars%n", cred, dol);
    }
    protected void actingFail(){
        System.out.println("You did not act successfully :(");
    }
    protected void cannotWorkWrapped(){
        System.out.println("You cannot work here. This scene is already wrapped");
    }
}
