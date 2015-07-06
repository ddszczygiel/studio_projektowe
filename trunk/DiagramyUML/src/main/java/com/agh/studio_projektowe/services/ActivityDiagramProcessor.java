package com.agh.studio_projektowe.services;

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
        BasicConfigurator.configure();
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
        activityDiagram.getInitialNode().getIn().add(initialNodeHandle);

        while (initialNodeHandle.getOut().get(0).getOut().size() > 0) {

            // TODO tu jest bug bo dwa razy z rzedu wchodze i znajduje loop ktore juz raz zostal podmieniony
            List<Node> treeNodes = getActualTreeElements(initialNodeHandle.getOut().get(0));
            displayNodesList(treeNodes);

            for (Finder finder : finders) {

                boolean found = false;
                int i = 0;
                while (i < treeNodes.size()) {

                    Node node = treeNodes.get(i++);
                    if (finder.find(node)) {
                        LOGGER.debug(String.format("PATTERN: %s FOUND. STARTING NODE NAME: %s", finder.getType(), node.getName()));
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

    public Node getInitialNodeHandle() {
        return initialNodeHandle;
    }

    public List<Node> getActualTreeElements(Node initialNode) {

        Queue<Node> nodeQueue = new LinkedList<>();
        Set<String> processedNodeNames = new HashSet<>();
        List<Node> nodes = new ArrayList<>();

        nodeQueue.add(initialNode);
        processedNodeNames.add(initialNode.getName());
        nodes.add(initialNode);

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

    private void displayNodesList(List<Node> nodes) {

        for (Node node : nodes) {
            System.out.println(node);
        }
    }
}
