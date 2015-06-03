package com.agh.studio_projektowe.error;


public enum ErrorType {

    FILE_NOT_EXIST("File not exist"),
    IO_ERROR("I/O error occurred"),
    UNKNOWN_LTL_PATTERN("Unknown LTL pattern found"),
    BAD_LTL_STRUCTURE("Error occurred while LTL pattern parsing - wrong pattern structure");

    private final String message;

    private ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
