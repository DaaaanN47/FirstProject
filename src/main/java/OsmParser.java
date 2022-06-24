import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class OsmParser {

    private static final String TAG_HIGHWAY = "highway";
    private static final String TAG_TRUCK = "truck";
    private static final String TAG_PRIMARY = "primary";
    private static final String TAG_SECONDARY = "secondary";
    private static final String TAG_TERTIARY = "tertiary";
    private static final String TAG_UNCLASSIFIED = "unclassified";
    private static final String TAG_RESIDENTIAL = "residential";
    private static final String TAG_MOTORWAY_LINK = "motorway_link";
    private static final String TAG_TRUCK_LINK = "trunk_link";
    private static final String TAG_PRIMARY_LINK = "primary_link";
    private static final String TAG_SECONDARY_LINK = "secondary_link";
    private static final String TAG_TERTIARY_LINK = "tertiary_link";
    private static final String TAG_LIVING_STREET = "living_street";
    private static final String TAG_SERVICE = "service";
    public static Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(new File("/home/kochnev_a/projects/untitled/src/NAB-CH.osm"));
    }

    public static void CheckWayParams(NodeList tagList){
        for (int j=0; j < tagList.getLength(); j++) {
            Node refNode = tagList.item(j);
            if (tagList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            NamedNodeMap refAttributes = refNode.getAttributes();
            if (refNode.getNodeName().equals("tag")) {
                System.out.println(refAttributes.getNamedItem("k").getNodeValue());
                String str = refAttributes.getNamedItem("k").getNodeValue();
                if (str.equals(TAG_HIGHWAY)) {
                    System.out.println("Это как минимум дорога");
                    break;
                }
                continue;
            }

            System.out.println("ref: " + refAttributes.getNamedItem("ref").getNodeValue());
        }
    }
    // тестовый метод того как выявлять дороги для машин
   private static Boolean CheckHighwayAttribute(NodeList taglist, NamedNodeMap refAttributes, int tagsStartPoint){
        for(int i = tagsStartPoint; i<taglist.getLength(); i++){
            if(refAttributes.getNamedItem("k").getNodeValue().equals("highway")){
                if(refAttributes.getNamedItem("v").getNodeValue().equals("highway")){

                }
            }
            else {
                continue;
            }
        }
        return null;
    }
}
//блок иф на всякий если не будет идей
//if(str ==TAG_HIGHWAY || str == TAG_PRIMARY || str ==TAG_PRIMARY_LINK ||
//        str ==TAG_LIVING_STREET || str == TAG_RESIDENTIAL || str ==TAG_SECONDARY ||
//        str ==TAG_TRUCK ||  str ==TAG_TERTIARY || str ==TAG_UNCLASSIFIED ||
//        str ==TAG_MOTORWAY_LINK || str == TAG_SECONDARY_LINK || str ==TAG_TRUCK_LINK ||
//        str ==TAG_TERTIARY_LINK || str ==TAG_SERVICE ){
//        System.out.println("Это как минимум дорога");
//        break;
//        }