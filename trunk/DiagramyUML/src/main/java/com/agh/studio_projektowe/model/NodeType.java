package com.agh.studio_projektowe.model;


public enum NodeType {

    ACTIVITY("Activity"),
    ACTIVITY_ACTION("ActivityAction"),
    ACTIVITY_FINAL_NODE("ActivityFinalNode"),
    DECISION_NODE("DecisionNode"),
    INITIAL_NODE("InitialNode"),
    FORK_NODE("ForkNode"),
    JOIN_NODE("JoinNode"),
    COMPLEX_NODE("");   // FIXME - this enum is information for ComplexNode class, that this type is one of the LTLPatternTypes !!

    private final String nodeTag;

    private NodeType(String nodeTag) {
        this.nodeTag = nodeTag;
    }

    public String getNodeTag() {
        return nodeTag;
    }

    public static NodeType getNodeTypeByTag(String tag) {

        for (NodeType nodeType : values()) {
            if (nodeType.getNodeTag().equals(tag)) {
                return nodeType;
            }
        }

        return null;
    }

}
