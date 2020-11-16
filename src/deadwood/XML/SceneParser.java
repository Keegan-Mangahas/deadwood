package deadwood.XML;

import deadwood.Role;
import deadwood.Scene;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

class SceneParser extends XMLParser {
    public ArrayList<Scene> readCardData(Document doc) {
        ArrayList<Scene> allCards = new ArrayList<Scene>(); //arraylist of cards to return;

        Element root = doc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        //for each 'card'
        for (int i = 0; i < cards.getLength(); i++) {
            Node card = cards.item(i);
            String cardName;
            String cardImg;
            String cardBudget;
            String sceneNumber = "";
            String sceneDescription = "";
            int totalRoles = 0;
            ArrayList<Role> roles = new ArrayList<Role>();


            //get the name of the card, budget, and image
            //instead of printing create card object?
            cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            cardImg = card.getAttributes().getNamedItem("img").getNodeValue();
            cardBudget = card.getAttributes().getNamedItem("budget").getNodeValue();

            NodeList cardChildren = card.getChildNodes();
            for (int j = 0; j < cardChildren.getLength(); j++) {
                Node sub = cardChildren.item(j);

                //get scene data
                if ("scene".equals(sub.getNodeName())) {
                    sceneNumber = sub.getAttributes().getNamedItem("number").getNodeValue();
                    sceneDescription = sub.getTextContent();
                } else if ("part".equals(sub.getNodeName())) {
                    Role tempRole = new Role();

                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    String partLevel = sub.getAttributes().getNamedItem("level").getNodeValue();

                    tempRole.roleName = partName;
                    tempRole.roleDifficulty = Integer.parseInt(partLevel);

                    totalRoles++;

                    NodeList partData = sub.getChildNodes();

                    for (int x = 0; x < partData.getLength(); x++) {
                        Node partDataNode = partData.item(x);
                        if ("area".equals(partDataNode.getNodeName())) {
                            String pX = partDataNode.getAttributes().getNamedItem("x").getNodeValue();
                            String pY = partDataNode.getAttributes().getNamedItem("y").getNodeValue();
                            String pW = partDataNode.getAttributes().getNamedItem("w").getNodeValue();
                            String pH = partDataNode.getAttributes().getNamedItem("h").getNodeValue();

                            tempRole.x = pX;
                            tempRole.y = pY;
                            tempRole.w = pW;
                            tempRole.h = pH;

                        }
                        if ("line".equals(partDataNode.getNodeName())) {
                            String line = partDataNode.getTextContent();

                            tempRole.roleDescription = line;

                        }
                    }
                    roles.add(tempRole);
                }
            }
            Scene newScene = new Scene(cardName, cardImg, Integer.parseInt(cardBudget), sceneNumber, sceneDescription, totalRoles, roles);
            allCards.add(newScene);
        }
        return allCards;
    }
}