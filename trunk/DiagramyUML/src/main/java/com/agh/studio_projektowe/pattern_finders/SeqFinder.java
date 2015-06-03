package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;


public class SeqFinder implements Finder {

    @Override
    public boolean find(Node startNode) {

        if (!CommonOperations.validateOutConnectionsCount(startNode, 1) || (!startNode.isRegularNode())) {
            return false;
        }

        Node nextElement = startNode.getOut().get(0);
        if (!CommonOperations.validateOutConnectionsCount(nextElement, 1) && (!nextElement.isRegularNode()))  {
            return false;
        }

        ComplexNode complexNode = new ComplexNode(LTLPatternType.SEQ);
        CommonOperations.prepareActualParamsArray(complexNode.getActualParams(), startNode, nextElement);

        // replace all references to startNode in all Nodes present on its IN list with complexNode
        // AND add complexNode
        CommonOperations.replaceParentConnections(startNode, complexNode);

        // replace all references to nextElement in all Nodes present on its OUT list with complexNode
        CommonOperations.replaceChildConnections(nextElement, complexNode);

        return true;
    }

    public static void main(String[] args) {

        Node a = new Node(NodeType.INITIAL_NODE, "", "a");
        Node b = new Node(NodeType.ACTIVITY_ACTION, "", "b");
        Node c = new Node(NodeType.ACTIVITY_FINAL_NODE, "", "c");

        a.getOut().add(b);
        b.getIn().add(a);
        b.getOut().add(c);
        c.getIn().add(b);

        SeqFinder seqFinder = new SeqFinder();
        System.out.println(seqFinder.find(b));
        Node after = a.getOut().get(0);
        System.out.println(after);
        ComplexNode complexNode = (ComplexNode) after;
        System.out.println(complexNode.getPattern());
        System.out.println(complexNode.getActualParams()[0]);
        System.out.println(complexNode.getActualParams()[1]);
        System.out.println(complexNode.getIn().size());
        System.out.println(complexNode.getOut().size());

        // "simple" test - looks ok
    }

}
