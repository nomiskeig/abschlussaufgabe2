package edu.kit.informatik.logic;

public class GameException extends Exception {
    /**
     * The default constructor.
     */
    public GameException() {
        super();
    }

    /**
     * The constructor.
     *
     * @param s the message of the exception.
     */
    public GameException(String s) {
        super(s);
    }
}
