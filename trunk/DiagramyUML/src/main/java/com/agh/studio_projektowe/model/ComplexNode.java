package com.agh.studio_projektowe.model;


public class ComplexNode extends Node {

    private static int counter = 1;

    private LTLPattern pattern;
    private Object[] actualParams;

    public ComplexNode(LTLPatternType patternType) {

        super(NodeType.COMPLEX_NODE, "", "COMPLEX_NODE_" + counter++);
        pattern = LTLPattern.getLTLPatternByType(patternType);
        actualParams = new Object[patternType.getInParamsCount()];  // array is already prepared !
    }

    public Object[] getActualParams() {
        return actualParams;
    }

    public LTLPattern getPattern() {
        return pattern;
    }

    public boolean isRegularNode() {
        return true;
    }

}
