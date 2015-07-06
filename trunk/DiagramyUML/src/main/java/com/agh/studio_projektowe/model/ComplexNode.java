package com.agh.studio_projektowe.model;


import com.google.common.base.Joiner;

public class ComplexNode extends Node {

    private static int counter = 1;

    private LTLPattern pattern;
    // FIXME temporary
    private LTLPatternType patternType;
    private Object[] actualParams;

    public ComplexNode(LTLPatternType patternType) {

        super(NodeType.COMPLEX_NODE, "", "COMPLEX_NODE_" + counter++);
        pattern = LTLPattern.getLTLPatternByType(patternType); //FIXME - for now it is null because no pattern reading mechanism
        actualParams = new Object[patternType.getInParamsCount()];  // array is already prepared !
        this.patternType = patternType;
    }

    public Object[] getActualParams() {
        return actualParams;
    }

    public LTLPattern getPattern() {
        return pattern;
    }

    public String getRepresentation() {

        StringBuilder builder = new StringBuilder();
        String[] paramsStrings = new String[actualParams.length];
        builder.append(patternType.toString());
        builder.append("(");

        int i = 0;
        for (Object param : actualParams) {

            if (param instanceof String) {

                paramsStrings[i++] = (String) param;
            } else {

                // param is instance of complexNode
                ComplexNode complexNode = (ComplexNode) param;
                paramsStrings[i++] = complexNode.getRepresentation();
            }
        }

        builder.append(Joiner.on(',').join(paramsStrings));
        builder.append(")");

        return builder.toString();
    }

    @Override
    public boolean isRegularNode() {
        return true;
    }

}
