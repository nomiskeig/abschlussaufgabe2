/*
 * Copyright (c) 2021, KASTEL. All rights reserved.
 */

package edu.kit.informatik.ui;

/**
 * This class is taken from the sample solution of task sheet 5. I put the ResultType enum in a separate file. This
 * class describes a result of a command execution.
 *
 * @author Lucas Alber
 * @version 1.0
 */
public class Result {

    private final ResultType type;
    private final String message;

    /**
     * Constructs a new Result without message.
     *
     * @param type the type of the result.
     */
    public Result(final ResultType type) {
        this(type, null);
    }

    /**
     * Constructs a new Result with message.
     *
     * @param type    the type of the result.
     * @param message message to carry
     */
    public Result(final ResultType type, final String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Returns the type of result.
     *
     * @return the type of result.
     */
    public ResultType getType() {
        return this.type;
    }

    /**
     * Returns the carried message of the result or {@code null} if there is none.
     *
     * @return the message or {@code null}
     */
    public String getMessage() {
        return this.message;
    }


}