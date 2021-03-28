package edu.kit.informatik.ui;

/**
 * This class models the parse exception. It is thrown when an error occurs during parsing of the program arguments.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class ParseException extends Exception {
    /**
     * The default constructor.
     */
    public ParseException() {
        super();
    }

    /**
     * The constructor.
     *
     * @param s the message of the exception.
     */
    public ParseException(String s) {
        super(s);
    }
}
