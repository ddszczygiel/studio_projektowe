package com.agh.studio_projektowe.parser;

import com.agh.studio_projektowe.error.ErrorType;
import com.agh.studio_projektowe.error.FunctionalException;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.Relation;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityDiagramParser {

    private XMLReader reader;
    private ActivityDiagramHandler handler;

    public ActivityDiagramParser() throws FunctionalException {

        try {
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } catch (Exception e) {
            throw new FunctionalException(ErrorType.SAX_READER_ERROR);
        }

        handler = new ActivityDiagramHandler();
        reader.setContentHandler(handler);
    }

    public void parse(InputStream inputStream) throws FunctionalException {

        if ( inputStream == null ) {
            throw new FunctionalException(ErrorType.FILE_NOT_EXIST);
        }

        try {
            reader.parse(new InputSource(inputStream));
        } catch (IOException e) {
            throw new FunctionalException(ErrorType.IO_ERROR);
        } catch (SAXException e) {
            throw new FunctionalException(ErrorType.SAX_PARSER_ERROR);
        }
    }

    public List<Node> getNodes() {

        // to prevent modifying originally parsed nodes
        List<Node> nodesCopy = new ArrayList<>();
        for (Node node : handler.getNodes()) {
            nodesCopy.add(new Node(node));
        }

        return nodesCopy;
    }

    public List<Relation> getRelations() {

        return handler.getRelationList();
    }

}
