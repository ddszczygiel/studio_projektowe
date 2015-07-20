package com.agh.studio_projektowe.services;


import com.agh.studio_projektowe.model.ComplexNode;
import com.google.common.base.Joiner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PatternProcessor {

    public String getModelRegularExpression(ComplexNode node) {

        StringBuilder builder = new StringBuilder();
        String[] paramsStrings = new String[node.getActualParams().length];
        builder.append(node.getPatternType());  // temporary
        builder.append("(");

        int i = 0;
        for (Object param : node.getActualParams()) {

            if (param instanceof String) {
                paramsStrings[i++] = (String) param;
            } else {
                // param is instance of complexNode
                ComplexNode complexNode = (ComplexNode) param;
                paramsStrings[i++] = getModelRegularExpression(complexNode);
            }
        }

        builder.append(Joiner.on(", ").join(paramsStrings));
        builder.append(")");

        return builder.toString();
    }

    private void getComplexNodeFromParams(ComplexNode node, List<ComplexNode> allNodes) {

        allNodes.add(node);
        for (Object param : node.getActualParams()) {
            if (param instanceof ComplexNode) {
                getComplexNodeFromParams((ComplexNode) param, allNodes);
            }
        }
    }

    private List<ComplexNode> getAllComplexNodes(ComplexNode startNode) {

        List<ComplexNode> complexNodes = new ArrayList<>();
        getComplexNodeFromParams(startNode, complexNodes);
        Collections.reverse(complexNodes);  // !!!! list is starting from most inner ComplexNode elements

        return complexNodes;
    }

    public List<String> getLogicalSpec(ComplexNode startNode) {

        List<String> patternLogic = new ArrayList<>();
        List<ComplexNode> complexNodes = getAllComplexNodes(startNode);
        Map<String, String> computedNodes = new HashMap<>();    // complexNode name + his alternate

        // the most inner complexNodes would have only simple parameters so starting analyzing from them should be ok
        for (ComplexNode node : complexNodes) {

            int i = 0;
            String[] actualStringParams = new String[node.getActualParams().length];

            for (Object param : node.getActualParams()) {

                if (param instanceof String) {
                    actualStringParams[i++] = (String) param;
                } else {
                    // param is complexNode that should have benn computed before and be present on map
                    String name = ((ComplexNode) param).getName();
                    String childAlternate = computedNodes.get(name);
                    actualStringParams[i++] = childAlternate;
                }
            }

            patternLogic.addAll(replaceLogicParams(node, actualStringParams));
            patternLogic.add("\n");
            computedNodes.put(node.getName(), getIniFinAlternateString(node, actualStringParams));
        }

        return patternLogic;
    }

    private String getIniFinAlternateString(ComplexNode node, String[] actualStringParams) {

        StringBuilder builder = new StringBuilder();
        builder.append("( ");
        builder.append(node.getPattern().getIni()).append(" | ").append(node.getPattern().getFin());
        builder.append(" )");

        String alternate = builder.toString();

        int i = 0;
        for (String param : node.getPattern().getParams()) {
            alternate = alternate.replaceAll(param, actualStringParams[i++]);
        }

        return alternate;
    }

    private List<String> replaceLogicParams(ComplexNode node, String[] actualParams) {

        List<String> logicLines = new ArrayList<>();
        String[] logic = node.getPattern().getLogic();
        String[] params = node.getPattern().getParams();

        for (String line : logic) {

            String newLine = line;
            for (int i = 0; i < params.length; i++) {
                newLine = newLine.replaceAll(params[i], actualParams[i]);
            }
            logicLines.add(newLine);
        }

        return logicLines;
    }

}
