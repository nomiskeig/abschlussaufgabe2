package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ForestField implements Field {
    private List<FireEngine> fireEngines;
    private FireState fireState;

    public ForestField(FireState initialState) {
        this.fireEngines = new ArrayList<>();
        this.fireState = initialState;
    }

    public ForestField(FireEngine fireEngine) {
        this.fireState = FireState.DRY;
        this.fireEngines = new ArrayList<>();
        this.fireEngines.add(fireEngine);
    }

    @Override
    public FireState getFireState() {
        return this.fireState;
    }

    @Override
    public void setFireState(FireState fs) {
        this.fireState = fs;
    }

    @Override
    public void placeFireEngine(FireEngine fireEngine) throws GameException {
        this.fireEngines.add(fireEngine);
    }

    @Override
    public Collection<FireEngine> getFireEngines(Player player) {
        Collection<FireEngine> fireEnginesOfPlayer = new ArrayList<>();
        for (FireEngine fe : this.fireEngines) {
            if (fe.getOwningPlayer() == player) {
                fireEnginesOfPlayer.add(fe);
            }
        }
        return fireEnginesOfPlayer;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.fireState.getDisplayName());
        //TODO: sort engines
        for (FireEngine fe : fireEngines) {
            result.append("," + fe.toString());
        }
        return result.toString();
    }
}
