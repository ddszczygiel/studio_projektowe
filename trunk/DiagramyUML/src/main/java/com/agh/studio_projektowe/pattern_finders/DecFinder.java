package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import org.springframework.stereotype.Component;

@Component
public class DecFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (startNode.getType() != NodeType.DECISION_NODE || !CommonOperations.validateOutConnectionsCount(startNode, 2)) {
            return false;
        }

        Node node1 = startNode.getOut().get(0);
        Node node2 = startNode.getOut().get(1);

        if (!CommonOperations.validateOutConnectionsCount(node1, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        if (!CommonOperations.validateOutConnectionsCount(node2, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        // FIXME not sure if this is correct !!! - checking if out's of node1 and node2 are linked to the same node ??
        if (node1.getOut().get(0) != node2.getOut().get(0)) {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(getType());
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, node1, node2);
        CommonOperations.replaceParentConnections(startNode, complexNode);
        CommonOperations.replaceChildConnections(node1.getOut().get(0), complexNode);

        return true;
    }

    @Override
    public LTLPatternType getType() {
        return LTLPatternType.DEC;
    }
}
