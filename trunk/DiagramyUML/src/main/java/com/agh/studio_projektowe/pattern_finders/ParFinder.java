package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;

public class ParFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (!CommonOperations.validateOutConnectionsCount(startNode, 1) || (!startNode.isRegularNode()))  {
            return false;
        }

        Node possibleFork = startNode.getOut().get(0);
        if (possibleFork.getType() != NodeType.FORK_NODE) {
            return false;
        }

        Node first = possibleFork.getOut().get(0);
        Node second = possibleFork.getOut().get(1);

        if (!CommonOperations.validateOutConnectionsCount(first, 1) || (!first.isRegularNode()))  {
            return false;
        }

        if (!CommonOperations.validateOutConnectionsCount(second, 1) || (!second.isRegularNode()))  {
            return false;
        }

        // check if out Nodes of first and second point to the same element [compare references]
        if (first.getOut().get(0) != second.getOut().get(0)) {
            return false;
        }

        Node possibleJoin = first.getOut().get(0);
        if (possibleJoin.getType() != NodeType.JOIN_NODE) {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(getType());
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, first, second);
        CommonOperations.replaceParentConnections(startNode, complexNode);
        CommonOperations.replaceChildConnections(possibleJoin, complexNode);

        return true;
    }

    @Override
    public LTLPatternType getType() {
        return LTLPatternType.PAR;
    }

}
