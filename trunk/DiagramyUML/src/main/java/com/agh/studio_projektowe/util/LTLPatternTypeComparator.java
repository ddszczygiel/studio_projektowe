package com.agh.studio_projektowe.util;


import com.agh.studio_projektowe.model.LTLPatternType;

import java.util.Comparator;

public class LTLPatternTypeComparator implements Comparator<LTLPatternType> {

    @Override
    public int compare(LTLPatternType o1, LTLPatternType o2) {
        return -Integer.compare(o1.getPriority(), o2.getPriority());
    }
}
