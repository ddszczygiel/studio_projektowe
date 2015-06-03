package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;

public class LoopFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (!CommonOperations.validateOutConnectionsCount(startNode, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        Node possibleDecision = startNode.getOut().get(0);
        if ((possibleDecision.getType() != NodeType.DECISION_NODE) ||
             CommonOperations.validateOutConnectionsCount(possibleDecision, 2)  ||
             CommonOperations.validateInConnectionsCount(possibleDecision, 2)) {
            return false;
        }

        Node loopOperationNode = null;
        Node endNode = null;
        for (Node out : possibleDecision.getOut()) {
            if ((out.getSpecificOutNodeIndex(possibleDecision.getName()) != -1) && out.isRegularNode()) {
                loopOperationNode = out;
            } else {
                endNode = out;
            }
        }

        if (loopOperationNode == null) {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(getType());
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, possibleDecision, loopOperationNode);
        CommonOperations.replaceParentConnections(startNode, complexNode);
        CommonOperations.replaceChildConnections(endNode, complexNode);

        return true;
    }

    @Override
    public LTLPatternType getType() {
        return LTLPatternType.LOOP;
    }

}
