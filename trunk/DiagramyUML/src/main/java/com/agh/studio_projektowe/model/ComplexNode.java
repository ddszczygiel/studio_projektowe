package com.agh.studio_projektowe.model;


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
        this.patternType = patternType;     // fixme to delete
    }

    // FIXME temporary
    public LTLPatternType getPatternType() {
        return patternType;
    }

    public Object[] getActualParams() {
        return actualParams;
    }

    public LTLPattern getPattern() {
        return pattern;
    }

    @Override
    public boolean isRegularNode() {
        return true;
    }

}
