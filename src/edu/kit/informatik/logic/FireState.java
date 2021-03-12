package edu.kit.informatik.logic;

public enum FireState {
    DRY("d"), WET("w"), LIGHT_FIRE("+"), STRONG_FIRE("*");


    private String displayName;

    FireState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static FireState getByName(String name) {
        for (FireState fs : FireState.values()) {
            if (fs.displayName.equals(name)) {
                return fs;
            }
        }
        return null;
    }
}
