package workWithXml;

import Figures.Cross;
import Figures.Zero;
import field.Field;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static MainWindow.Main.fields;

public class workWithXml{

    public static String getGameFieldState(){
        String result = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            Document doc = factory.newDocumentBuilder().newDocument();
            Element root = doc.createElement("Fields");
            doc.appendChild(root);

            for (Field field:fields) {
                Element element = doc.createElement("Field");
                element.setTextContent(field.getSide());
                root.appendChild(element);
            }
            result = xmlToString(doc);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void setGameFieldState(String xmlString){
        Document doc = stringToXml(xmlString);
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        int i = 0;
        for (Field field:fields) {

            if (nodeList.item(i).getTextContent().equals("Cross")){
                Cross cross = new Cross(field);
                field.drawFigure(cross);
                field.setSide("Cross");
            }else {
                if (nodeList.item(i).getTextContent().equals("Zero")) {
                    Zero zero = new Zero(field);
                    field.drawFigure(zero);
                    field.setSide("Zero");
                }
            }
            i++;
        }
    }

    public static String xmlToString(Document doc){
        doc.setXmlVersion("1.1");
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        return ser.writeToString(doc);
    }

    public static Document stringToXml(String strXML){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try
        {
            builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new InputSource(new StringReader(strXML)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String checkForWinner(Document doc){
        Element root = doc.getDocumentElement();
        String result = null;
        NodeList nodeList = root.getChildNodes();

        for(int i = 0; i <= 6; i += 3){
            Node element = nodeList.item(i);
            Node element2 = nodeList.item(i + 1);
            Node element3 = nodeList.item(i + 2);
            if((element.getTextContent().equals(element2.getTextContent())) && (element.getTextContent().equals(element3.getTextContent()))){
                if(element.getTextContent() != null)
                    result = element.getTextContent();
            }
        }

        for(int i = 0; i <= 2; i++){
            Node element = nodeList.item(i);
            Node element2 = nodeList.item(i + 3);
            Node element3 = nodeList.item(i + 6);
            if((element.getTextContent().equals(element2.getTextContent())) && (element.getTextContent().equals(element3.getTextContent()))){
                if(element.getTextContent() != null)
                    result = element.getTextContent();
            }
        }

        Node element0 = nodeList.item(0);
        Node element4 = nodeList.item(4);
        Node element8 = nodeList.item(8);
        if((element0.getTextContent().equals(element4.getTextContent())) && (element0.getTextContent().equals(element8.getTextContent()))){
            if(element0.getTextContent() != null)
                result = element0.getTextContent();
        }

        Node element2 = nodeList.item(2);
        Node element6 = nodeList.item(6);
        if((element2.getTextContent().equals(element4.getTextContent())) && (element2.getTextContent().equals(element6.getTextContent()))){
            if(element2.getTextContent() != null)
                result = element2.getTextContent();
        }

        if (result == null){
            int counter = 0;
            for(int i = 0; i < nodeList.getLength(); i++)
                if ((nodeList.item(i).getTextContent() != null) && (!nodeList.item(i).getTextContent().equals("")))
                    counter++;
            result = counter == 9 ? "Draw" : null;
            System.out.println(counter);
        }

        return result;
    }
}

