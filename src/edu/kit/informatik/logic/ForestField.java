package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.ArrayList;
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
        if (this.fireState == FireState.LIGHT_FIRE) {
            throw new GameException(Errors.NO_ENGINE_ON_LIGHT);
        }
        if (this.fireState == FireState.STRONG_FIRE) {
            throw new GameException(Errors.NO_ENGINE_ON_STRONG);
        }
        this.fireEngines.add(fireEngine);
    }

    @Override
    public void removeFireEngine(FireEngine fireEngine) {
        this.fireEngines.remove(fireEngine);
    }

    @Override
    public List<FireEngine> getFireEngines(Player player) {
        List<FireEngine> fireEnginesOfPlayer = new ArrayList<>();
        for (FireEngine fe : this.fireEngines) {
            if (fe.getOwningPlayer() == player) {
                fireEnginesOfPlayer.add(fe);
            }
        }
        return fireEnginesOfPlayer;
    }

    @Override
    public boolean isPond() {
        return false;
    }

    @Override
    public boolean isFireStation(Player player) {
        return false;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.fireState.getDisplayName());
        //TODO: sort engines
        for (FireEngine fe : fireEngines) {
            result.append("," + fe.getId());
        }
        return result.toString();
    }
}
