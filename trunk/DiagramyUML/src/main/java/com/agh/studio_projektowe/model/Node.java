package com.agh.studio_projektowe.model;


import java.util.ArrayList;
import java.util.List;

public class Node {

    private String id;
    private String name;
    private NodeType type;
    private List<Node> out;
    private List<Node> in;  // need to have parents of element

    public Node(NodeType type, String id, String name) {

        this.name = name;
        this.id = id;
        this.type = type;
        out = new ArrayList<>();
        in = new ArrayList<>();
    }

    public Node(Node node) {

        this(node.getType(), node.getId(), node.getName());
    }

    public NodeType getType() {
        return type;
    }

    public List<Node> getOut() {
        return out;
    }

    public List<Node> getIn() {
        return in;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpecificInNodeIndex(String name) {

        for (int i = 0; i < in.size(); i++) {
            if (in.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;  // FIXME should not happen !!!
    }

    public int getSpecificOutNodeIndex(String name) {

        for (int i = 0; i < out.size(); i++) {
            if (out.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;  // FIXME should not happen !!!
    }

    public String toString() {

        return String.format("%30s %40s %30s", id, name, type);
    }

    public boolean isRegularNode() {

        return ((type == NodeType.ACTIVITY_ACTION) || (type == NodeType.ACTIVITY) || (type == NodeType.INITIAL_NODE) ||
                (type == NodeType.ACTIVITY_FINAL_NODE));        // ??? check if final is good !
    }

}
