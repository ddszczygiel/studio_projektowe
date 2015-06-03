package com.agh.studio_projektowe.pattern_finders;


import com.agh.studio_projektowe.model.LTLPatternType;
import com.agh.studio_projektowe.model.Node;

public interface Finder {

    boolean find(Node startNode);

    LTLPatternType getType();
}
