package deadwood;

import java.util.ArrayList;
import java.util.Collection;

public class Set {
    protected String setName;
    protected String x;
    protected String y;
    protected String w;
    protected String h;
    protected int totalTakes;
    protected Scene currentScene;
    //protected Collection<Set> adjacentSets = new ArrayList<>();
    protected ArrayList<String> neighborNames = new ArrayList<String>();
    protected ArrayList<Role> roles = new ArrayList<Role>();
    protected ArrayList<Take> takesData = new ArrayList<Take>();


    public Set(String name, String sX, String sY, String sW, String sH, int totalTakesNum, ArrayList neighbors, ArrayList setRoles, ArrayList takeList) {
        setName = name;
        x = sX;
        y = sY;
        w = sW;
        h = sH;
        totalTakes = totalTakesNum;
        neighborNames = neighbors;
        roles = setRoles;
        takesData = takeList;

    }


    protected Collection<Player> playersInRoom = new ArrayList<>();

    public Set(String name) {
        this.setName = name;
    }

    private Collection<Player> getPlayersInRoom() {
        return playersInRoom;
    }

    private void addPlayer(Player playerToAdd) {
    }

    private void removePlayer(Player playerToAdd) {
    }

    private boolean checkInRoom(Player player) {
        return false;
    }

    private void getScene(){
        //get data from the scene card in the room
    }

    public void printSceneInfo(){
        System.out.printf("Set name: %s, Total Takes: %d, Set Area(x, y, w, h): %s %s %s %s%n", setName, totalTakes, x, y, w, h);
        for(int i = 0; i < neighborNames.size(); i++){
            System.out.println("    Neighbor: " + neighborNames.get(i));
        }
    }
}
