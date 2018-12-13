import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Created by compu on 03/06/2018.
 **/
public class CustomerAddressArray {

    private static String addressPrint;

    public static String createArray() {

    try {
        File inputFile = new File("out" + File.separator + "customers.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document inputDoc = dBuilder.parse(inputFile);
        inputDoc.getDocumentElement().normalize();
        System.out.println(inputDoc.getDocumentElement().getNodeName());
        NodeList nList = inputDoc.getElementsByTagName("customer");
        System.out.println("----------------------------");

        String temp;

        //for (int i = 0; i < nList.getLength(); i++) {
        //    Node nNode = nList.item(i);
        Node nNode = nList.item(0);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                System.out.print("Customer name : ");
                System.out.println(eElement.getAttribute("name"));
                addressPrint = eElement.getAttribute("name");
                NodeList addressFields = eElement.getChildNodes();

                for (int j = 0; j < addressFields.getLength(); j++) {
                    Node n = addressFields.item(j);

                    if(n.getNodeType() == Node.ELEMENT_NODE) {

                        switch (n.getNodeName()) {
                            case "address1":
                                temp = n.getTextContent();
                                break;
                            case "address2":
                                temp = n.getTextContent();
                                break;
                            case "towncity":
                                temp = n.getTextContent();
                                break;
                            case "county":
                                temp = n.getTextContent();
                                break;
                            case "postcode":
                                temp = n.getTextContent();
                                break;
                            default:
                                temp = "";
                                break;

                        }
                        if (temp.length() > 0) {
                            addressPrint += "\n" + temp;
                        }
                    }
                }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        return addressPrint;
}
}
