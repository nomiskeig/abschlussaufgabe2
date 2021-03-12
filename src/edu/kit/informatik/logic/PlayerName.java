package edu.kit.informatik.logic;

public enum PlayerName {
    PLAYER_A("A"), PLAYER_B("B"), PLAYER_C("C"), PLAYER_D("D");
    String name;

    PlayerName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
