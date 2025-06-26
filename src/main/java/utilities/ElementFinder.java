package utilities;

import com.codeborne.selenide.SelenideElement;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class ElementFinder {
    private static Document doc;

    static {
        try {
            File file = new File("src/test/resources/ObjectRepository.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load Object Repository (ObjectRepository.xml)");
        }
    }

    public static SelenideElement getElement(String pageName, String logicalName) {
        NodeList pageList = doc.getElementsByTagName("Page");

        for (int i = 0; i < pageList.getLength(); i++) {
            Element page = (Element) pageList.item(i);
            if (page.getAttribute("name").equalsIgnoreCase(pageName)) {
                NodeList elements = page.getElementsByTagName("WebElement");

                for (int j = 0; j < elements.getLength(); j++) {
                    Element element = (Element) elements.item(j);
                    if (element.getAttribute("logicalName").equalsIgnoreCase(logicalName)) {
                        String type = element.getAttribute("locatorType").trim();
                        String value = element.getAttribute("locatorValue").trim();

                        switch (type.toLowerCase()) {
                            case "id": return $("#" + value);
                            case "name": return $("[name='" + value + "']");
                            case "xpath": return $x(value);
                            case "css": return $(value);
                            case "classname": return $("." + value);
                            case "linktext": return $x("//a[text()='" + value + "']");
                            default: throw new RuntimeException("Invalid locator type: " + type);
                        }
                    }
                }
            }
        }
        throw new RuntimeException("Locator not found for " + logicalName + " in " + pageName);
    }
}
