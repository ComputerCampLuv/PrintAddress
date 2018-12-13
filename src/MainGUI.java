import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.*;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainGUI extends Application {

    public ArrayList<String> address = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Create stage
        Stage stage = primaryStage;
        stage.setTitle("Address Entry");

        //Create Layout
        GridPane gridLayout = new GridPane();
        gridLayout.setPadding(new Insets(10,10,10,10));
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        gridLayout.setAlignment(Pos.CENTER);

        //Create labels
        Label nameLabel = new Label("Name");
        gridLayout.add(nameLabel, 0,0);
        Label address1Label = new Label("Address Line 1");
        gridLayout.add(address1Label, 0,1);
        Label address2Label = new Label("Address Line 2");
        gridLayout.add(address2Label, 0,2);
        Label townCityLabel = new Label("Town/City");
        gridLayout.add(townCityLabel, 0,3);
        Label countyLabel = new Label("County");
        gridLayout.add(countyLabel, 0,4);
        Label postcodeLabel = new Label("Postcode");
        gridLayout.add(postcodeLabel, 0,5);

        //Create TextFields
        TextField name = new TextField();
        name.setPromptText("Required");
        gridLayout.add(name, 1,0);
        TextField address1 = new TextField();
        address1.setPromptText("Required");
        gridLayout.add(address1, 1,1);
        TextField address2 = new TextField();
        address2.setPromptText("Optional");
        gridLayout.add(address2, 1,2);
        TextField townCity = new TextField();
        townCity.setPromptText("Optional");
        gridLayout.add(townCity, 1,3);
        TextField county = new TextField();
        county.setPromptText("Optional");
        gridLayout.add(county, 1,4);
        TextField postcode = new TextField();
        postcode.setPromptText("Required");
        gridLayout.add(postcode, 1,5);

        //Create submit button
        Button button = new Button("Add Customer");
        button.setOnAction(event -> {

                address.add(name.getText());
                address.add(address1.getText());
                address.add(address2.getText());
                address.add(townCity.getText());
                address.add(county.getText());
                address.add(postcode.getText());
                System.out.println("The size of the address Array list is : " + address.size() + "\n------------------");
                //addressPDF();
                addressToXML();
                CustomerAddressArray.createArray();
                Notification.displayNotification("Confirmation", "Customer added to XML");
                try {
                    CreatePDF.createPdf();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            address.clear();
        });

        gridLayout.add(button,1, 6);


        //Create and set scene
        Scene scene = new Scene(gridLayout,400,400);
        stage.setScene(scene);

        //Display Stage
        stage.show();
    }



    private void addressPDF() {

        try {
            String dest = "out" + File.separator + "addressPDF.pdf";
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(dest)));
            doc.open();

            float llx = 36;
            float lly = 36;
            float urx = 559;
            float ury = 806;
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
            rect1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            rect1.setBorder(Rectangle.BOX);
            rect1.setBorderWidth(1);
            canvas.rectangle(rect1);
            Rectangle rect2 = new Rectangle(llx + 60, lly, urx, ury - 40);
            rect2.setBackgroundColor(BaseColor.DARK_GRAY);
            rect2.setBorder(Rectangle.BOX);
            rect2.setBorderColor(BaseColor.WHITE);
            rect2.setBorderWidth(0.5f);
            canvas.rectangle(rect2);

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph p = new Paragraph();
            Chunk c = new Chunk("", font);

            for (int i = 0; i < address.size(); i++) {
                if (address.get(i).length() != 0) {
                    c.append(address.get(i) + "\n");
                }
            }
            p.add(c);
            doc.add(p);
            doc.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (DocumentException de) {
            de.printStackTrace();
        }

    }

    private void addressToXML() {

        boolean newFile = true;
        org.w3c.dom.Document document;
        Element rootElement;
        File file = new File("out" + File.separator + "customers.xml");

        if (file.exists() && !file.isDirectory()) {
            newFile = false;
        }

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            if (!newFile) {
                File xmlFile = new File("out" + File.separator + "customers.xml");
                document = builder.parse(xmlFile);
                rootElement = document.getDocumentElement();
            } else {
                document = builder.newDocument();
                rootElement = document.createElement("customers");
                document.appendChild(rootElement);
            }

            // Customer element
            Element customer = document.createElement("customer");
            rootElement.appendChild(customer);

            // Attach name to customer element
            Attr nameX = document.createAttribute("name");
            nameX.setValue(address.get(0));
            customer.setAttributeNode(nameX);

            // Address Line 1
            Element address1X = document.createElement("address1");
            address1X.appendChild(document.createTextNode(address.get(1)));
            customer.appendChild(address1X);

            // Address Line 2
            Element address2X = document.createElement("address2");
            address2X.appendChild(document.createTextNode(address.get(2)));
            customer.appendChild(address2X);

            // Town / City
            Element townCityX = document.createElement("towncity");
            townCityX.appendChild(document.createTextNode(address.get(3)));
            customer.appendChild(townCityX);

            // County
            Element countyX = document.createElement("county");
            countyX.appendChild(document.createTextNode(address.get(4)));
            customer.appendChild(countyX);

            // Postcode
            Element postcodeX = document.createElement("postcode");
            postcodeX.appendChild(document.createTextNode(address.get(5)));
            customer.appendChild(postcodeX);

            // Write XML to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File("out" + File.separator + "customers.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
