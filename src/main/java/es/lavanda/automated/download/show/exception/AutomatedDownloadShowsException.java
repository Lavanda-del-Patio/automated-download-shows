package com.lavanda.automated.download.shows.exception;

public class AutomatedDownloadShowsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutomatedDownloadShowsException(String message) {
        super(message);
    }

    public AutomatedDownloadShowsException(String message, Exception e) {
        super(message, e);
    }
}