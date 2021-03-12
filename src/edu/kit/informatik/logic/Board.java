package edu.kit.informatik.logic;

import edu.kit.informatik.Terminal;

import java.util.Arrays;

public class Board {

    private Field[][] board;

    public Board(Field[][] fields) {
        this.board = fields;
    }

    @Override
    public String toString() {
        String result = "";
        for (Field[] row : board) {
            for (Field field : row) {
                result += field.toString();
            }
            result += System.lineSeparator();
        }
        return result;
    }
}
