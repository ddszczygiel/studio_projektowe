package com.agh.studio_projektowe.model;


public enum LTLPatternType {

    SEQ(1, 2),
    SEQSEQ(2, 3),
    PAR(4, 3),
    DEC(3, 3),
    LOOP(5, 3);

    private int priority;
    private final int inParamsCount;

    private LTLPatternType(int priority, int inParamsCount) {
        this.priority = priority;
        this.inParamsCount = inParamsCount;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getInParamsCount() {
        return inParamsCount;
    }

    public static LTLPatternType getPatternByName(String name) {

        for (LTLPatternType patternType : values()) {
            if (patternType.name().equalsIgnoreCase(name)) {
                return patternType;
            }
        }

        return null;
    }

}
