package com.agh.studio_projektowe.controllers;

import com.agh.studio_projektowe.error.ErrorType;
import com.agh.studio_projektowe.error.FunctionalException;
import com.agh.studio_projektowe.model.ActivityDiagram;
import com.agh.studio_projektowe.model.ComplexNode;
import com.agh.studio_projektowe.model.service.ResponseObject;
import com.agh.studio_projektowe.parser.ActivityDiagramParser;
import com.agh.studio_projektowe.services.ActivityDiagramProcessor;
import com.agh.studio_projektowe.services.PatternProcessor;
import com.agh.studio_projektowe.util.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/data")
public class AnalyzerControler {

    @Autowired
    private ActivityDiagramParser activityDiagramParser;

    @Autowired
    private FileStatus fileStatus;

    @Autowired
    private ActivityDiagramProcessor activityDiagramProcessor;

    @Autowired
    private PatternProcessor patternProcessor;

    private void checkFilesLoaded() throws FunctionalException {

        if (!fileStatus.isLtlModels()) {
            throw new FunctionalException(ErrorType.LTL_MODELS_FILE_MISSING);
        }

        if (!fileStatus.isActivityDiagram()) {
            throw new FunctionalException(ErrorType.ACTIVITY_DIAGRAM_FILE_MISSING);
        }
    }

    @RequestMapping(value = "/ltlpattern", method = RequestMethod.GET)
    public ResponseObject getLTLPattern() {

        ResponseObject responseObject = new ResponseObject();
        try {

            checkFilesLoaded();
            ActivityDiagram activityDiagram = new ActivityDiagram(activityDiagramParser.getNodes(), activityDiagramParser.getRelations());
            activityDiagramProcessor.processActivityDiagram(activityDiagram);
            ComplexNode firstNode = (ComplexNode) activityDiagramProcessor.getInitialNodeHandle().getOut().get(0);
            String ltlPattern = patternProcessor.getModelRegularExpression(firstNode);
            responseObject.setPayload(ltlPattern);
        } catch (FunctionalException e) {
            responseObject.setErrorMessage(e.getMessage());
        }

        return responseObject;
    }

    @RequestMapping(value = "/ltlspec", method = RequestMethod.GET)
    public ResponseObject getLTLSpec() {

        ResponseObject responseObject = new ResponseObject();
        try {

            checkFilesLoaded();
            ActivityDiagram activityDiagram = new ActivityDiagram(activityDiagramParser.getNodes(), activityDiagramParser.getRelations());
            activityDiagramProcessor.processActivityDiagram(activityDiagram);
            ComplexNode firstNode = (ComplexNode) activityDiagramProcessor.getInitialNodeHandle().getOut().get(0);
            List<String> ltlPattern = patternProcessor.getLogicalSpec(firstNode);
            responseObject.setPayload(ltlPattern);
        } catch (FunctionalException e) {
            responseObject.setErrorMessage(e.getMessage());
        }

        return responseObject;
    }

}
