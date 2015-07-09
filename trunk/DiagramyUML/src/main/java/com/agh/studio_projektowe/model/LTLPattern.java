package com.agh.studio_projektowe.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LTLPattern {

    private static Map<LTLPatternType, LTLPattern> patterns = new HashMap<>();

    private LTLPatternType type;
    private String[] params;
    private String ini;
    private String fin;
    private String[] logic;

    public LTLPattern(LTLPatternType type, String[] params, String ini, String fin, String[] logic) {

        this.type = type;
        this.params = params;
        this.ini = ini;
        this.fin = fin;
        this.logic = logic;
        patterns.put(type, this);
    }

    public String[] getParams() {
        return params;
    }

    public String getIni() {
        return ini;
    }

    public String getFin() {
        return fin;
    }

    public String[] getLogic() {
        return logic;
    }

    public LTLPatternType getType() {
        return type;
    }

    @Override
    public String toString() {

        List<String> params = Arrays.asList(this.params);
        List<String> logic = Arrays.asList(this.logic);
        return String.format("%s\n%s\n%s\n%s\n%s", type, params, ini, fin, logic);
    }

    public static LTLPattern getLTLPatternByType(LTLPatternType type) {

        return patterns.get(type);
    }


}