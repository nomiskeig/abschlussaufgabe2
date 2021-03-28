package edu.kit.informatik.ui;

/**
 * The type of Result of a execution. I extracted this enum from the result class, which I took from the example
 * solution of assignment 5.
 *
 * @author Simon Giek
 * @author Lucas Alber
 * @version 2.0
 */
public enum ResultType {
    /**
     * The execution did not end with success.
     */
    FAILURE,
    /**
     * The execution did end with success.
     */
    SUCCESS,
    /**
     * The execution quit the program.
     */
    QUIT
}
