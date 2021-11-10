package es.lavanda.automated.download.show.exception;

public class AutomatedDownloadShowsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutomatedDownloadShowsException(String message) {
        super(message);
    }

    public AutomatedDownloadShowsException(String message, Exception e) {
        super(message, e);
    }
}