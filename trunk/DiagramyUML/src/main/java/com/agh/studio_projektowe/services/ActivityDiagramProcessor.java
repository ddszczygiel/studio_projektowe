package com.agh.studio_projektowe.services;

import com.agh.studio_projektowe.model.ActivityDiagram;
import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import com.agh.studio_projektowe.pattern_finders.Finder;
import com.agh.studio_projektowe.util.FinderComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class ActivityDiagramProcessor {

    @Autowired
    private List<Finder> finders;

    private Node initialNodeHandle;

    public ActivityDiagramProcessor() {

        this.finders = new ArrayList<>();
    }

    @PostConstruct
    public void setUp() {
        Collections.sort(finders, new FinderComparator());
    }

    public List<Finder> getFinders() {
        return finders;
    }

    public void processActivityDiagram(ActivityDiagram activityDiagram) {

        initialNodeHandle = new Node(NodeType.COMPLEX_NODE, "P", "handle");

        if (activityDiagram.getInitialNode() == null) {
            // TODO bad diagram structure !! handle error
            // probably exception is thrown
        }
        initialNodeHandle.getOut().add(activityDiagram.getInitialNode());

        while (!(initialNodeHandle.getOut().get(0) instanceof ComplexNode)) {

            List<Node> treeNodes = getAllTreeNodes(initialNodeHandle.getOut().get(0));

            for (Finder finder : finders) {

                boolean found = false;
                for (Node node : treeNodes) {

                    if (finder.find(node)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    break;
                }
            }
        }
    }

    private List<Node> getAllTreeNodes(Node initialNode) {

        List<Node> allNodes = new ArrayList<>();
        Queue<Node> children = new LinkedList<>();
        children.add(initialNode);

        while (children.size() > 0) {

            Node node = children.poll();
            allNodes.add(node);
            for (Node out : node.getOut()) {
                children.add(out);
            }
        }

        return allNodes;
    }

    public Node getInitialNodeHandle() {
        return initialNodeHandle;
    }
}
