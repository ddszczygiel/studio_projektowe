package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import org.springframework.stereotype.Component;

@Component
public class LoopFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (!CommonOperations.validateOutConnectionsCount(startNode, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        Node decisionNode = startNode.getOut().get(0);
        if ((decisionNode.getType() != NodeType.DECISION_NODE) ||
             !CommonOperations.validateOutConnectionsCount(decisionNode, 2)  ||
             !CommonOperations.validateInConnectionsCount(decisionNode, 2)) {
            return false;
        }

        Node loopOperationNode = null;
        Node outNode = null;
        for (Node out : decisionNode.getOut()) {
            // there are only 2 out connections so one should be operation node and second out node
            if ((out.getSpecificOutNodeIndex(decisionNode.getName()) != -1) && out.isRegularNode()) {
                loopOperationNode = out;
            } else {
                outNode = out;
            }
        }

        if (loopOperationNode == null) {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(getType());
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, decisionNode, loopOperationNode);
        CommonOperations.replaceParentConnections(startNode, complexNode);
//        THAT WONT WORK BECAUSE OF 2 OUT CONNECTIONS FROM DECISION NODE !!!
//        CommonOperations.replaceChildConnections(decisionNode, complexNode);
        int pos = outNode.getSpecificInNodeIndex(decisionNode.getName());
        outNode.getIn().remove(pos);
        outNode.getIn().add(complexNode);
        complexNode.getOut().add(outNode);

        return true;
    }

    @Override
    public LTLPatternType getType() {
        return LTLPatternType.LOOP;
    }

}
