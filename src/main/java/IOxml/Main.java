import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String average = "0";
        Student student;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название файла для чтения:");
        String name = scanner.nextLine();
        System.out.println("Введите название файла для записи:");
        String newName = scanner.nextLine();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(name));
            NodeList nodeListName = document.getElementsByTagName("student");
            String lastName = nodeListName.item(0).getAttributes().item(0).getTextContent();
            System.out.println(nodeListName.item(0).getAttributes().item(0));
            NodeList nodeListSubject = document.getElementsByTagName("subject");
            List<Subject> subjects = new ArrayList<>();
            Document newDocument = builder.newDocument();
            Element studentElement = newDocument.createElement("student");
            studentElement.setAttribute("lastname", lastName);
            newDocument.appendChild(studentElement);
            if (nodeListSubject.getLength() != 0) {
                for (int i = 0; i < nodeListSubject.getLength(); i++) {
                    subjects.add(new Subject(nodeListSubject.item(i).getAttributes().item(1)
                            .getTextContent(), nodeListSubject.item(i)
                            .getAttributes().item(0).getTextContent()));
                    System.out.println(nodeListSubject.item(i).getAttributes().item(1).getTextContent());
                    System.out.println(nodeListSubject.item(i).getAttributes().item(0).getTextContent());
                    Element subjectElement = newDocument.createElement("subject");
                    subjectElement.setAttribute("title", nodeListSubject.item(i).getAttributes()
                            .item(1).getTextContent());
                    subjectElement.setAttribute("mark", nodeListSubject.item(i).getAttributes()
                            .item(0).getTextContent());
                    studentElement.appendChild(subjectElement);
                }
            }
            NodeList nodeListAverage = document.getElementsByTagName("average");
            if (nodeListAverage.getLength() != 0) {
                average = nodeListAverage.item(0).getTextContent();
                System.out.println(average);
                if (nodeListSubject.getLength() != 0) {
                    student = new Student(subjects, Double.parseDouble(average));
                } else {
                    student = new Student(Double.parseDouble(average));
                }
            } else {
                if (nodeListSubject.getLength() != 0) {
                    student = new Student(subjects);
                } else {
                    student = new Student();
                }
            }
            double currentAverage = student.averageTrue();
            System.out.println(currentAverage);
            if (Double.parseDouble(average) != currentAverage) {
                average = String.valueOf(currentAverage);
            }
            Element averageElement = newDocument.createElement("average");
            averageElement.setTextContent(average);
            studentElement.appendChild(averageElement);

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(newDocument), new StreamResult(new FileOutputStream(newName)));
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
