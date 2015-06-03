package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.Node;

public final class CommonOperations {

    private CommonOperations() {}

    public static boolean validateOutConnectionsCount(Node node, int count) {

        return (node.getOut().size() == count);
    }

    public static void replaceParentConnections(Node begin, Node replacement) {

        String beginName = begin.getName();
        for (Node inNode : begin.getIn()) {

            int pos = inNode.getSpecificOutNodeIndex(beginName);
            inNode.getOut().remove(pos);
            inNode.getOut().add(replacement);
            replacement.getIn().add(inNode);    // add element to complexNode IN list
        }
    }

    public static void replaceChildConnections(Node end, Node replacement) {

        String endName = end.getName();
        for (Node outNode : end.getOut()) {

            int pos = outNode.getSpecificInNodeIndex(endName);
            outNode.getIn().remove(pos);
            outNode.getIn().add(replacement);
            replacement.getOut().add(outNode);      // add element to complexNode OUT list
        }
    }

    public static void prepareActualParamsArray(Object[] array, Node... nodes) {

        for (int i = 0; i < array.length; i++) {
            if (nodes[i] instanceof ComplexNode) {
                array[i] = nodes[i];
            } else {
                array[i] = nodes[i].getName();
            }
        }
    }

}
