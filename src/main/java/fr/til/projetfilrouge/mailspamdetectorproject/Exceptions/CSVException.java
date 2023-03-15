package fr.til.projetfilrouge.mailspamdetectorproject.Exceptions;

public class CSVException extends Exception{

    public CSVException(String message) {
        super(message);
    }

    public CSVException(String message, Throwable cause) {
        super(message, cause);
    }
}