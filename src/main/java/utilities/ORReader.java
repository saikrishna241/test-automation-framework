package utilities;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ORReader {

    public static String getLocator(String pageName, String logicalName) throws Exception {
        File file = new File("src/test/resources/ObjectRepository.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList pageList = doc.getElementsByTagName("Page");

        for (int i = 0; i < pageList.getLength(); i++) {
            Element pageElement = (Element) pageList.item(i);
            if (pageElement.getAttribute("name").equals(pageName)) {
                NodeList webElements = pageElement.getElementsByTagName("WebElement");
                for (int j = 0; j < webElements.getLength(); j++) {
                    Element webElement = (Element) webElements.item(j);
                    if (webElement.getAttribute("logicalName").equals(logicalName)) {
                        return webElement.getAttribute("locatorValue");
                    }
                }
            }
        }
        throw new Exception("Locator not found for: " + logicalName + " on page: " + pageName);
    }

    public static String getLocatorType(String pageName, String logicalName) throws Exception {
        // Returns "xpath", "css", etc.
        File file = new File("src/test/resources/ObjectRepository.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList pageList = doc.getElementsByTagName("Page");

        for (int i = 0; i < pageList.getLength(); i++) {
            Element pageElement = (Element) pageList.item(i);
            if (pageElement.getAttribute("name").equals(pageName)) {
                NodeList webElements = pageElement.getElementsByTagName("WebElement");
                for (int j = 0; j < webElements.getLength(); j++) {
                    Element webElement = (Element) webElements.item(j);
                    if (webElement.getAttribute("logicalName").equals(logicalName)) {
                        return webElement.getAttribute("locatorType");
                    }
                }
            }
        }
        throw new Exception("Locator type not found for: " + logicalName + " on page: " + pageName);
    }
}
