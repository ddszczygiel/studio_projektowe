package com.agh.studio_projektowe.services;

import com.agh.studio_projektowe.error.ErrorType;
import com.agh.studio_projektowe.error.FunctionalException;
import com.agh.studio_projektowe.model.ActivityDiagram;
import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import com.agh.studio_projektowe.pattern_finders.Finder;
import com.agh.studio_projektowe.util.FinderComparator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

@Component
public class ActivityDiagramProcessor {

    private final Logger LOGGER = Logger.getLogger(ActivityDiagramProcessor.class);

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

    public void processActivityDiagram(ActivityDiagram activityDiagram) throws FunctionalException {

        if (activityDiagram.getInitialNode() == null) {
            throw new FunctionalException(ErrorType.BAD_ACTIVITY_DIAGRAM_STRUCTURE);
        }

        initialNodeHandle = new Node(NodeType.COMPLEX_NODE, "P", "handle");
        initialNodeHandle.getOut().add(activityDiagram.getInitialNode());
        activityDiagram.getInitialNode().getIn().add(initialNodeHandle);

        while (initialNodeHandle.getOut().get(0).getOut().size() > 0) {

            List<Node> treeNodes = getActualTreeElements(initialNodeHandle.getOut().get(0));
            int findersChecked = 0;

            for (Finder finder : finders) {

                boolean found = false;
                int i = 0;
                findersChecked++;

                while (i < treeNodes.size()) {

                    Node node = treeNodes.get(i++);
                    if (finder.find(node)) {

//                        LOGGER.debug(String.format("PATTERN: %s FOUND. STARTING NODE NAME: %s", finder.getType(), node.getName()));
                        found = true;
                        break;
                    }
                }

                if (found) {
                    break;
                } else {
                    if (findersChecked == finders.size()) {
                        throw new FunctionalException(ErrorType.UNKNOWN_ACTIVITY_DIAGRAM_STRUCTURE);
                    }
                }
            }
        }
    }

    public Node getInitialNodeHandle() {
        return initialNodeHandle;
    }

    private List<Node> getActualTreeElements(Node startNode) {

        Queue<Node> nodeQueue = new LinkedList<>();
        Set<String> processedNodeNames = new HashSet<>();
        List<Node> nodes = new ArrayList<>();

        nodeQueue.add(startNode);
        processedNodeNames.add(startNode.getName());
        nodes.add(startNode);

        while (!nodeQueue.isEmpty()) {

            Node element = nodeQueue.poll();
            for (Node out : element.getOut()) {

                if (!processedNodeNames.contains(out.getName())) {

                    nodeQueue.add(out);
                    nodes.add(out);
                    processedNodeNames.add(out.getName());
                }
            }
        }

        return nodes;
    }

}
