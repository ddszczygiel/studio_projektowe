package com.agh.studio_projektowe;


import com.agh.studio_projektowe.model.ActivityDiagram;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.parser.ActivityDiagramParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestClass {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        ActivityDiagramParser parser = new ActivityDiagramParser();
        Long start = System.currentTimeMillis();
        parser.parse("./resources/wplata.xml");
        Long stop = System.currentTimeMillis();
        System.out.println(stop-start);


        ActivityDiagram activityDiagram = new ActivityDiagram(parser.getNodes(), parser.getRelations());
        List<Node> nodes = activityDiagram.getNodes();

//        for (Node n : nodes) {
//            System.out.println(n);
//        }


        Queue<Node> queue = new LinkedList<>();
        queue.add(activityDiagram.getInitialNode());

        while(queue.size() > 0) {
            Node node = queue.poll();
            System.out.println(node);
            for (Node n : node.getOut()) {
                queue.add(n);
            }
        }
    }
}

