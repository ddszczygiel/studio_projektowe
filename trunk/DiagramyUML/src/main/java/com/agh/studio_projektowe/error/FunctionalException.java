package com.agh.studio_projektowe.error;



public class FunctionalException  extends Exception {

    public FunctionalException(ErrorType errorType) {
        super(errorType.getMessage());
    }

}
