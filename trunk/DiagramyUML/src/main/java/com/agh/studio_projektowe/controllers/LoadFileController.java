package com.agh.studio_projektowe.controllers;


import com.agh.studio_projektowe.error.FunctionalException;
import com.agh.studio_projektowe.model.service.ResponseObject;
import com.agh.studio_projektowe.parser.ActivityDiagramParser;
import com.agh.studio_projektowe.parser.ConfigurationParser;
import com.agh.studio_projektowe.parser.LTLModelsParser;
import com.agh.studio_projektowe.util.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/load")
public class LoadFileController {

    @Autowired
    private ActivityDiagramParser activityDiagramParser;

    @Autowired
    private LTLModelsParser ltlModelsParser;

    @Autowired
    private ConfigurationParser configurationParser;

    @Autowired
    private FileStatus fileStatus;

    @RequestMapping(value = "/conffile", method = RequestMethod.POST)
    public ResponseObject loadConfiguration(@RequestParam(value = "file", required = false) MultipartFile file) {

        ResponseObject responseObject = new ResponseObject();
        try {

            configurationParser.loadConfig(file.getOriginalFilename());
            responseObject.setPayload(Boolean.TRUE);
        } catch (FunctionalException e) {
            responseObject.setErrorMessage(e.getMessage());
        }

        return responseObject;
    }

    @RequestMapping(value = "/activitydiagram", method = RequestMethod.POST)
    public ResponseObject loadActivityDiagram(@RequestParam(value = "file", required = false) MultipartFile file) {

        ResponseObject responseObject = new ResponseObject();
        try {

            activityDiagramParser.parse(file.getOriginalFilename());
            fileStatus.setActivityDiagram(true);
            responseObject.setPayload(Boolean.TRUE);
        } catch (FunctionalException e) {
            responseObject.setErrorMessage(e.getMessage());
        }

        return responseObject;
    }

    @RequestMapping(value = "/ltlmodels", method = RequestMethod.POST)
    public ResponseObject loadLTLModels(@RequestParam(value = "file", required = false) MultipartFile file) {

        ResponseObject responseObject = new ResponseObject();
        try {

            ltlModelsParser.getPatterns(file.getOriginalFilename());
            fileStatus.setLtlModels(true);
            responseObject.setPayload(Boolean.TRUE);
        } catch (FunctionalException e) {
            responseObject.setErrorMessage(e.getMessage());
        }

        return responseObject;
    }
}

