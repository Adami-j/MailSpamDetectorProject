package fr.til.projetfilrouge.mailspamdetectorproject.Exceptions;

public class CSVException extends Exception{

    /**
     * @author MateoIsabey
     * Gestion des erreurs CSV
     * @param message
     */
    public CSVException(String message) {
        super(message);
    }

    /**
     * @author MateoIsabey
     * Gestion des erreurs CSV
     * @param message
     * @param cause
     */
    public CSVException(String message, Throwable cause) {
        super(message, cause);
    }
}