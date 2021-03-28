package edu.kit.informatik.logic;

/**
 * This class models the game exception. It is thrown when an error occurs during execution of an command.
 *
 * @author Simon Giek
 * @version 1.0
 */
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
