package com.agh.studio_projektowe.error;


public enum ErrorType {

    FILE_NOT_EXIST("File not exist"),
    IO_ERROR("I/O error occurred"),
    UNKNOWN_LTL_PATTERN("Unknown LTL pattern found"),
    BAD_LTL_STRUCTURE("Error occurred while LTL pattern parsing - wrong pattern structure"),
    SAX_PARSER_ERROR("Error occurred during XML parsing"),
    SAX_READER_ERROR("Can not create SAX parser instance"),
    UNKNOWN_ACTIVITY_DIAGRAM_STRUCTURE("Unknown activity diagram structure (unknown patterns exist)"),
    BAD_ACTIVITY_DIAGRAM_STRUCTURE("Diagram initial node not found"),
    UNKNOWN_CONF_PATTERN("Configuration file contains unknown LTL pattern - default values are used"),
    MISSING_PATTERN_CONF("Missing configuration for some LTL patterns - default values are used"),
    LTL_MODELS_FILE_MISSING("Missing LTL models configuration file"),
    ACTIVITY_DIAGRAM_FILE_MISSING("Missing activity diagram file");


    private final String message;

    private ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
