package com.agh.studio_projektowe.parser;


import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import com.agh.studio_projektowe.model.Relation;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ActivityDiagramHandler extends DefaultHandler {

    private List<Node> nodes;
    private List<Relation> relationList;

    private boolean inModels;
    private boolean inModelChildren;
    private String parent;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if ("Models".equals(qName)) {
            inModels = true;
            nodes = new ArrayList<>();
            relationList = new ArrayList<>();
        }

        if ("ModelChildren".equals(qName)) {
            inModelChildren = true;
        }

        if (inModels) {

            switch(qName) {

                case "MasterView":
                    parent = qName;
                    break;
                case "ControlFlow":
                    if (inModelChildren && !"MasterView".equals(parent)) {
                        relationList.add(new Relation(attributes.getValue("From"), attributes.getValue("To")));
                    }
                    break;
            }

            NodeType nodeType = NodeType.getNodeTypeByTag(qName);
            if (nodeType != null && !"MasterView".equals(parent)) {

                Node node = new Node(nodeType, attributes.getValue("Id"), attributes.getValue("Name"));
                nodes.add(node);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        switch (qName) {

            case "MasterView":
                parent = null;
                break;
            case "Models":
                inModels = false;
                break;
            case "ModelChildren":
                inModelChildren = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // nothing as a value
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Relation> getRelationList() {
        return relationList;
    }
}
