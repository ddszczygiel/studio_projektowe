package com.agh.studio_projektowe.util;

import com.agh.studio_projektowe.pattern_finders.Finder;

import java.util.Comparator;

public class FinderComparator implements Comparator<Finder> {

    @Override
    public int compare(Finder o1, Finder o2) {
        return -Integer.compare(o1.getType().getPriority(), o2.getType().getPriority());
    }
}
