package com.agh.studio_projektowe.model;


import java.util.List;

public class ActivityDiagram {

    private List<Node> nodes;
    private List<Relation> relationList;

    public ActivityDiagram(List<Node> nodes, List<Relation> relationList) {

        this.nodes = nodes;
        this.relationList = relationList;
        buildDiagram();
    }

    private Node getNodeById(String id) {

        for (Node node : nodes) {
            if (id.equals(node.getId())) {
                return node;
            }
        }

        return null;    // FIXME this should not happen !!! means not existing node
    }

    public Node getInitialNode() {

        for (Node node : nodes) {
            if (node.getType() == NodeType.INITIAL_NODE) {
                return node;
            }
        }

        return null;        // FIXME important !!! every activityDiagram must have InitialNode !!!
    }

    public List<Node> getNodes() {
        return nodes;
    }

    private void buildDiagram() {

        for (Relation rel : relationList) {

            Node n1 = getNodeById(rel.getFrom());
            Node n2 = getNodeById(rel.getTo());
            n1.getOut().add(n2);
            n2.getIn().add(n1);

        }
    }
}
