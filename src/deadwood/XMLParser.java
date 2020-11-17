package deadwood;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLParser{
    public Document getDocFromFile(String filename)
    throws ParserConfigurationException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try{
            doc = db.parse(filename);
        }
        catch(Exception ex){
            System.out.println("Board XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    }

    public ArrayList<Scene> readCardData(Document doc){
        ArrayList<Scene> allCards = new ArrayList<Scene>(); //arraylist of cards to return;

        Element root = doc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        //for each 'card'
        for(int i = 0; i < cards.getLength(); i++){
            Node card = cards.item(i);
            String cardName;
            String cardImg;
            String cardBudget;
            String sceneNumber = "";
            String sceneDescription = "";
            int totalRoles = 0;
            ArrayList<Role> roles = new ArrayList<Role>();



            cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            cardImg = card.getAttributes().getNamedItem("img").getNodeValue();
            cardBudget = card.getAttributes().getNamedItem("budget").getNodeValue();

            NodeList cardChildren = card.getChildNodes();
            for(int j = 0; j < cardChildren.getLength(); j++){
                Node sub = cardChildren.item(j);

                //get scene data
                if("scene".equals(sub.getNodeName())){
                    sceneNumber = sub.getAttributes().getNamedItem("number").getNodeValue();
                    sceneDescription = sub.getTextContent();
                }
                else if("part".equals(sub.getNodeName())){
                    Role tempRole = new Role();

                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    String partLevel = sub.getAttributes().getNamedItem("level").getNodeValue();
                    
                    tempRole.roleName = partName;
                    tempRole.roleDifficulty = Integer.parseInt(partLevel);

                    totalRoles++;

                    NodeList partData = sub.getChildNodes();

                    for(int x = 0; x < partData.getLength(); x++){
                        Node partDataNode = partData.item(x);
                        if("area".equals(partDataNode.getNodeName())){
                            String pX = partDataNode.getAttributes().getNamedItem("x").getNodeValue();
                            String pY = partDataNode.getAttributes().getNamedItem("y").getNodeValue();
                            String pW = partDataNode.getAttributes().getNamedItem("w").getNodeValue();
                            String pH = partDataNode.getAttributes().getNamedItem("h").getNodeValue();

                            tempRole.x = pX;
                            tempRole.y = pY;
                            tempRole.w = pW;
                            tempRole.h = pH;

                        }
                        if("line".equals(partDataNode.getNodeName())){
                            String line = partDataNode.getTextContent();

                            tempRole.roleDescription = line;

                        }
                    }
                    tempRole.starring = true;
                    roles.add(tempRole);
                }
            }
            Scene newScene = new Scene(cardName, cardImg, Integer.parseInt(cardBudget), sceneNumber, sceneDescription, totalRoles, roles);
            allCards.add(newScene);
        }
        return allCards;
    }

    //reads the board.xml file
    public ArrayList<Set> readBoardData(Document doc){
        ArrayList<Set> allSets = new ArrayList<Set>();
        Element root = doc.getDocumentElement();

        String trailerName = "trailer";
        ArrayList<String> trailerNeighbors = new ArrayList<>();
        ArrayList<Role> trailerRoles = new ArrayList<Role>();
        ArrayList<Take> trailerTakes = new ArrayList<Take>();
        String trailerX = "";
        String trailerY = "";
        String trailerW = "";
        String trailerH = "";

        NodeList trailers = root.getElementsByTagName("trailer");
        Node trailer = trailers.item(0);
        NodeList trailerChildren = trailer.getChildNodes();
        for(int i = 0; i < trailerChildren.getLength(); i++){
            Node sub = trailerChildren.item(i);
            //gets neighbor data
            if("neighbors".equals(sub.getNodeName())){
                Element el = (Element) trailerChildren.item(i);
                NodeList neighbors = el.getElementsByTagName("neighbor");
                for(int x = 0; x < neighbors.getLength(); x++){
                    Node neighbor = neighbors.item(x);
                    String neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();

                    trailerNeighbors.add(neighborName);
                }
            }
            else if("area".equals(sub.getNodeName())){
                String sX = sub.getAttributes().getNamedItem("x").getNodeValue();
                String sY = sub.getAttributes().getNamedItem("y").getNodeValue();
                String sW = sub.getAttributes().getNamedItem("w").getNodeValue();
                String sH = sub.getAttributes().getNamedItem("h").getNodeValue();

                trailerX = sX;
                trailerY = sY;
                trailerW = sW;
                trailerH = sH;
            }
        }
        Set trailerSet = new Set(trailerName, trailerX, trailerY, trailerW, trailerH, 0, trailerNeighbors, trailerRoles, trailerTakes);
        trailerSet.sceneDiscovered = true;
        allSets.add(trailerSet);

        String officeName = "office";
        ArrayList<String> officeNeighbors = new ArrayList<>();
        ArrayList<Role> officeRoles = new ArrayList<Role>();
        ArrayList<Take> officeTakes = new ArrayList<Take>();
        String officeX = "";
        String officeY = "";
        String officeW = "";
        String officeH = "";

        NodeList offices = root.getElementsByTagName("office");
        Node office = offices.item(0);
        NodeList officeChildren = office.getChildNodes();
        for(int i = 0; i < officeChildren.getLength(); i++){
            Node sub = officeChildren.item(i);
            //gets neighbor data
            if("neighbors".equals(sub.getNodeName())){
                Element el = (Element) officeChildren.item(i);
                NodeList neighbors = el.getElementsByTagName("neighbor");
                for(int x = 0; x < neighbors.getLength(); x++){
                    Node neighbor = neighbors.item(x);
                    String neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();

                    officeNeighbors.add(neighborName);
                }
            }
            else if("area".equals(sub.getNodeName())){
                String sX = sub.getAttributes().getNamedItem("x").getNodeValue();
                String sY = sub.getAttributes().getNamedItem("y").getNodeValue();
                String sW = sub.getAttributes().getNamedItem("w").getNodeValue();
                String sH = sub.getAttributes().getNamedItem("h").getNodeValue();

                officeX = sX;
                officeY = sY;
                officeW = sW;
                officeH = sH;
            }
        }
        Set officeSet = new Set(officeName, officeX, officeY, officeW, officeH, 0, officeNeighbors, officeRoles, officeTakes);
        allSets.add(officeSet);
        officeSet.sceneDiscovered = true;

        NodeList sets = root.getElementsByTagName("set");
        //for each 'set' on the board
        for (int i = 0; i < sets.getLength(); i++){ 
            ArrayList<String> neighborNames = new ArrayList<>();
            ArrayList<Role> roles = new ArrayList<Role>();
            ArrayList<Take> takesData = new ArrayList<Take>();
            String setName;
            String setX = "";
            String setY = "";
            String setW = "";
            String setH = "";
            int totalTakes = 0;

            Node set = sets.item(i);

            //get the sets name and print it
            //instead of printing, create Set object??
            setName = set.getAttributes().getNamedItem("name").getNodeValue();

            //get the rest of the elements in the current set
            NodeList setChildren = set.getChildNodes();
            for(int j = 0; j < setChildren.getLength(); j++){
                Node sub = setChildren.item(j);

                //gets neighbor data
                if("neighbors".equals(sub.getNodeName())){
                    Element el = (Element) setChildren.item(j);
                    NodeList neighbors = el.getElementsByTagName("neighbor");
                    for(int x = 0; x < neighbors.getLength(); x++){
                        Node neighbor = neighbors.item(x);
                        String neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();

                        neighborNames.add(neighborName);

                    }
                }
                //gets set area data
                else if("area".equals(sub.getNodeName())){
                    String sX = sub.getAttributes().getNamedItem("x").getNodeValue();
                    String sY = sub.getAttributes().getNamedItem("y").getNodeValue();
                    String sW = sub.getAttributes().getNamedItem("w").getNodeValue();
                    String sH = sub.getAttributes().getNamedItem("h").getNodeValue();

                    setX = sX;
                    setY = sY;
                    setW = sW;
                    setH = sH;

                }
                //gets takes/shotcounter data
                else if("takes".equals(sub.getNodeName())){
                    Element el = (Element) setChildren.item(j);
                    NodeList takes = el.getElementsByTagName("take");
                    totalTakes = takes.getLength();

                    //gets take data for later (gui stuff)
                    for(int x = 0; x < takes.getLength(); x++){
                        Take tempTake = new Take();

                        Node take = takes.item(x);
                        String takeNumber = take.getAttributes().getNamedItem("number").getNodeValue();

                        tempTake.takeNum = takeNumber;

                        Element takeEl = (Element) takes.item(x);
                        NodeList takeData = takeEl.getElementsByTagName("area");
                        for(int y = 0; y < takeData.getLength(); y++){
                            Node takeArea = takeData.item(y);
                            String tX = takeArea.getAttributes().getNamedItem("x").getNodeValue();
                            String tY = takeArea.getAttributes().getNamedItem("y").getNodeValue();
                            String tH = takeArea.getAttributes().getNamedItem("h").getNodeValue();
                            String tW = takeArea.getAttributes().getNamedItem("w").getNodeValue();

                            tempTake.x = tX;
                            tempTake.y = tY;
                            tempTake.h = tH;
                            tempTake.w = tW;
                        }
                        takesData.add(tempTake);
                    }
                }
                //gets set parts data
                else if("parts".equals(sub.getNodeName())){
                    
                    Element el = (Element) setChildren.item(j);
                    NodeList parts = el.getElementsByTagName("part");

                    for(int y = 0; y < parts.getLength(); y++){
                        Role tempRole = new Role();
                        Node part = parts.item(y);
                        String partName = part.getAttributes().getNamedItem("name").getNodeValue();
                        String difficulty = part.getAttributes().getNamedItem("level").getNodeValue();

                        tempRole.roleName = partName;
                        tempRole.roleDifficulty = Integer.parseInt(difficulty);


                        NodeList partData = part.getChildNodes();
                        for(int z = 0; z < partData.getLength(); z++){
                            Node partDataNode = partData.item(z);
                            if("area".equals(partDataNode.getNodeName())){
                                String pX = partDataNode.getAttributes().getNamedItem("x").getNodeValue();
                                String pY = partDataNode.getAttributes().getNamedItem("y").getNodeValue();
                                String pW = partDataNode.getAttributes().getNamedItem("w").getNodeValue();
                                String pH = partDataNode.getAttributes().getNamedItem("h").getNodeValue();

                                tempRole.x = pX;
                                tempRole.y = pY;
                                tempRole.w = pW;
                                tempRole.h = pH;

                            }
                            if("line".equals(partDataNode.getNodeName())){
                                String line = partDataNode.getTextContent();

                                tempRole.roleDescription = line;

                            }
                        }
                        
                    tempRole.starring = false;
                    roles.add(tempRole);
                    }
                }
            }
            Set newSet = new Set(setName, setX, setY, setW, setH, totalTakes, neighborNames, roles, takesData);
            allSets.add(newSet);
        }
        return allSets;
    }
}
