package edu.kit.informatik.logic;

public interface FireBreaker {
    void move(String id, int row, int column) throws GameException;

    String extinguish(String id, int row, int column) throws GameException;

    int refill(String id) throws GameException;

    int buyFireEngine(int row, int column) throws GameException;

    String turn() throws GameException;
    
    String showBoard();

    String showField(int row, int column) throws GameException;

    String showPlayer() throws GameException;

    String fireToRoll(int dice) throws GameException;

}
