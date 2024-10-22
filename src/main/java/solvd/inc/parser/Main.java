package solvd.inc.parser;

import solvd.inc.model.TaxiCompany;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String xmlPath = "C:\\Users\\Glebasher\\Desktop\\taxi-company\\src\\main\\java\\solvd\\inc\\parser\\data.xml";
        String xsdPath = "C:\\Users\\Glebasher\\Desktop\\taxi-company\\src\\main\\java\\solvd\\inc\\parser\\data.xsd";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a parser:");
        System.out.println("1. JAXB Parser");
        System.out.println("2. StAX Parser");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Using JAXB Parser");
                TaxiCompany taxiCompany = JaxbParser.unmarshalTaxiCompany(xmlPath);
                if (taxiCompany != null) {
                    System.out.println("Successfully unmarshalled TaxiCompany:");
                    JaxbParser.printTaxiCompanyData(taxiCompany);
                } else {
                    System.out.println("Failed to unmarshal TaxiCompany");
                }
                break;
            case 2:
                System.out.println("Using StAX Parser");
                StaxParser.validateXMLSchema(xsdPath, xmlPath);
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2");
                break;
        }

        scanner.close();
    }
}
