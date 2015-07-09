package com.agh.studio_projektowe;


import com.agh.studio_projektowe.model.ActivityDiagram;
import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.LTLPattern;
import com.agh.studio_projektowe.model.Node;
import com.agh.studio_projektowe.model.NodeType;
import com.agh.studio_projektowe.parser.ActivityDiagramParser;
import com.agh.studio_projektowe.parser.LTLModelsParser;
import com.agh.studio_projektowe.pattern_finders.DecFinder;
import com.agh.studio_projektowe.pattern_finders.Finder;
import com.agh.studio_projektowe.pattern_finders.LoopFinder;
import com.agh.studio_projektowe.pattern_finders.ParFinder;
import com.agh.studio_projektowe.pattern_finders.SeqFinder;
import com.agh.studio_projektowe.pattern_finders.SeqSeqFinder;
import com.agh.studio_projektowe.services.ActivityDiagramProcessor;
import com.agh.studio_projektowe.services.PatternProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SpringBootApplication
@ComponentScan("com.agh.studio_projektowe")
public class TestClass {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestClass.class, args);
        ActivityDiagramProcessor processor = (ActivityDiagramProcessor) applicationContext.getBean("activityDiagramProcessor");

        ActivityDiagramParser parser = new ActivityDiagramParser();
        parser.parse("./resources/ee2.xml");
        LTLModelsParser parser2 = new LTLModelsParser();
        List<LTLPattern> patterns = parser2.getPatterns("E:\\projects\\studio_projektowe\\trunk\\DiagramyUML\\resources\\temp_logic.txt");

        LoopFinder loopFinder = (LoopFinder) applicationContext.getBean("loopFinder");
        ParFinder parFinder = (ParFinder) applicationContext.getBean("parFinder");
        SeqSeqFinder seqSeqFinder = (SeqSeqFinder) applicationContext.getBean("seqSeqFinder");
        DecFinder decFinder = (DecFinder) applicationContext.getBean("decFinder");
        SeqFinder seqFinder = (SeqFinder) applicationContext.getBean("seqFinder");

        ActivityDiagram activityDiagram = new ActivityDiagram(parser.getNodes(), parser.getRelations());
        List<Node> nodes = activityDiagram.getNodes();

        PatternProcessor patternProcessor = (PatternProcessor) applicationContext.getBean("patternProcessor");

//        for (Node n : nodes) {
//            System.out.println(n);
//        }
//        for (Node node : processor.getActualTreeElements(activityDiagram.getInitialNode())) {
//            System.out.println(node);
//        }
//        Node start = nodes.get(0);
//        Node d = nodes.get(8);
//        Node dec1 = n);
//        Node c = nodes.get(7);
//        Node a = nodes.get(1);
//        Node handle = new Node(NodeType.COMPLEX_NODE, "", "");
//        handle.getOut().add(start);
//
////
//        System.out.println("dupa");
//        int i =0;
//        while (i < nodes.size()) {
//            boolean result = loopFinder.find(nodes.get(i));
//            System.out.println(result);
//            i++;
//            if (result) break;
//        }
//        System.out.println(loopFinder.find(d));
//        System.out.println(parFinder.find(b));
//        System.out.println(seqFinder.find(c));
//        System.out.println(decFinder.find(dec1));
//        System.out.println(seqSeqFinder.find(a));
//        System.out.println(seqFinder.find(start));
//        System.out.println("dupa");
//
//
//        Queue<Node> queue = new LinkedList<>();
//        queue.add(activityDiagram.getInitialNode());
//
//        while(queue.size() > 0) {
//            Node node = queue.poll();
//            System.out.println(node);
//            for (Node n : node.getOut()) {
//                queue.add(n);
//            }
//        }
//    }
//        System.out.println("AFTER \n \n \n");
//        for (Node node : processor.getActualTreeElements(activityDiagram.getInitialNode())) {
//            System.out.println(node);
//        }
//
        processor.processActivityDiagram(activityDiagram);
        ComplexNode handle = (ComplexNode) processor.getInitialNodeHandle().getOut().get(0);
        System.out.println(patternProcessor.getModelRegularExpression(handle));
        List<ComplexNode> complexNodes = patternProcessor.getAllComplexNodes(handle);
        System.out.println("dupa");
        for (String s : patternProcessor.processComplexNodes(handle)) {
            System.out.println(s);
        }
//        System.out.println(handle.getRepresentation());
//        System.out.println("end");

    }
}

