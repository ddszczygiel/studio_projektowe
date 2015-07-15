package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SeqFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (!CommonOperations.validateOutConnectionsCount(startNode, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        Node nextElement = startNode.getOut().get(0);
        if ((nextElement.getOut().size() > 1) || (!nextElement.isRegularNode()))  {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(getType());
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, nextElement);

        // replace all references to startNode in all Nodes present on its IN list with complexNode
        // AND add complexNode
        CommonOperations.replaceParentConnections(startNode, complexNode);

        // replace all references to nextElement in all Nodes present on its OUT list with complexNode
        CommonOperations.replaceChildConnections(nextElement, complexNode);

        return true;
    }

    @Override
    public LTLPatternType getType() {
        return LTLPatternType.SEQ;
    }

}
