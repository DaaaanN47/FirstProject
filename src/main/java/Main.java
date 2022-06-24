import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
//
public class Main {


    HashMap<Long, NodeOSM> nodes = new HashMap<>();
    HashMap<Long, Way> ways = new HashMap<>();
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Document document = OsmParser.getDocument();
        //NodeList rootNode = document.get
        NodeList wayList = document.getElementsByTagName("way");
        for(int i = 0; i<4; i++){
            Node way = wayList.item(i);
            NamedNodeMap attributes = way.getAttributes();
            System.out.println(attributes.getNamedItem("id").getNodeValue());
            NodeList tagList = way.getChildNodes();
            OsmParser.CheckWayParams(tagList);
        }


    }
}
