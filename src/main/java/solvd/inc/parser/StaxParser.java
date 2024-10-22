package solvd.inc.parser;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class StaxParser {

    public static void validateXMLSchema(String xsdPath, String xmlPath) {
        XMLStreamReader reader = null;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            reader = xmlInputFactory.createXMLStreamReader(new StreamSource(new File(xmlPath)));

            validator.validate(new StreamSource(new File(xmlPath)));

            System.out.println("XML is valid");
            printXMLData(reader);
        } catch (Exception e) {
            System.out.println("XML validation failed: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    System.out.println("Error closing XMLStreamReader: " + e.getMessage());
                }
            }
        }
    }

    private static void printXMLData(XMLStreamReader reader) {
        int depth = 0;

        try {
            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLStreamReader.START_ELEMENT) {
                    String elementName = reader.getLocalName();
                    System.out.print("    ".repeat(depth) + elementName + ": ");

                    if (reader.hasNext()) {
                        reader.next();
                        if (reader.isCharacters()) {
                            System.out.println(reader.getText().trim());
                        } else {
                            System.out.println();
                        }
                    }
                    depth++;
                } else if (event == XMLStreamReader.END_ELEMENT) {
                    depth--;
                }
            }
        } catch (XMLStreamException e) {
            System.out.println("Error reading XML file: " + e.getMessage());
        }
    }
}
