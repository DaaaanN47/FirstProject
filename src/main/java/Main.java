import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
//
public class Main {
    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        OsmParser osmParser = new OsmParser();
        Document document = osmParser.getDocument();
        Graph graph = osmParser.graph;
        NodeList wayList = document.getElementsByTagName("way");
        int wayListlength = wayList.getLength();

        for(int i = 0; i<wayListlength; i++) {
            Node way = wayList.item(i);
            osmParser.CheckWayParams(way);
        }
        NodeList nodeList = document.getElementsByTagName("node");
        int nodeListlength = nodeList.getLength();
        for(int i = 0; i< nodeListlength; i++){
            osmParser.GetNode(nodeList.item(i));
        }

        System.out.println("Madina privet");


    }
}
