package com.agh.studio_projektowe.util;


import org.springframework.stereotype.Component;

@Component
public class FileStatus {

    private boolean activityDiagram;
    private boolean ltlModels;

    public boolean isActivityDiagram() {
        return activityDiagram;
    }

    public boolean isLtlModels() {
        return ltlModels;
    }

    public void setActivityDiagram(boolean activityDiagram) {
        this.activityDiagram = activityDiagram;
    }

    public void setLtlModels(boolean ltlModels) {
        this.ltlModels = ltlModels;
    }
}
