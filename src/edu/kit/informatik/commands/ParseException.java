package edu.kit.informatik.commands;

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
