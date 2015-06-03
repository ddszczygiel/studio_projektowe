package com.agh.studio_projektowe.parser;


import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.Relation;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class ActivityDiagramParser {

    private XMLReader reader;
    private ActivityDiagramHandler handler;

    public ActivityDiagramParser() throws ParserConfigurationException, SAXException {

        reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        handler = new ActivityDiagramHandler();
        reader.setContentHandler(handler);
    }

    public void parse(String filePath) throws IOException, SAXException {
        reader.parse(new InputSource(new FileInputStream(filePath)));
    }

    public List<Node> getNodes() {
        return handler.getNodes();
    }

    public List<Relation> getRelations() {
        return handler.getRelationList();
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        ActivityDiagramParser parser = new ActivityDiagramParser();
        parser.parse("E:\\projects\\studio_projektowe\\trunk\\DiagramyUML\\resources\\ex1.xml");
        List<Node> nodes = parser.getNodes();
        for (Node n : nodes) {
            System.out.println(n);
        }
    }
}
