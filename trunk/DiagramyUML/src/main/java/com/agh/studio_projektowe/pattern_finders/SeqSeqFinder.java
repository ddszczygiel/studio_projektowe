package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import org.springframework.stereotype.Component;

@Component
public class SeqSeqFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (!CommonOperations.validateOutConnectionsCount(startNode, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        Node secondElement = startNode.getOut().get(0);
        if (!CommonOperations.validateOutConnectionsCount(secondElement, 1) || (!secondElement.isRegularNode()))  {
            return false;
        }

        Node thirdElement = secondElement.getOut().get(0);
        // last element could not have out elements ( for example END_NODE ) !!!! so other condition AND
//        END ELEMENT CA NOT HAVE MORE THAN ONE INPUT ELEMENTS !!!!
        if ((thirdElement.getOut().size() > 1) || (thirdElement.getIn().size() != 1) || (!thirdElement.isRegularNode()))  {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(getType());
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, secondElement, thirdElement);
        CommonOperations.replaceParentConnections(startNode, complexNode);
        CommonOperations.replaceChildConnections(thirdElement, complexNode);

        return true;
    }

    @Override
    public LTLPatternType getType() {
        return LTLPatternType.SEQSEQ;
    }

}
