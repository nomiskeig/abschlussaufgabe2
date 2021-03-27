package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForestField implements Field {
    private List<FireEngine> fireEngines;
    private FireState fireState;

    private boolean alreadyModified;

    public ForestField(FireState initialState) {
        this.fireEngines = new ArrayList<>();
        this.fireState = initialState;
        this.alreadyModified = false;
    }

    public ForestField(FireEngine fireEngine) {
        this.fireState = FireState.DRY;
        this.fireEngines = new ArrayList<>();
        this.fireEngines.add(fireEngine);
    }

    @Override
    public boolean alreadyModified() {
        return this.alreadyModified;
    }

    @Override
    public void resetModified() {
        this.alreadyModified = false;
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
    public boolean isFireStation() {
        return false;
    }

    @Override
    public void burn() {
        this.alreadyModified = true;
        switch (this.fireState) {
            case WET:
                this.fireState = FireState.DRY;
                break;
            case LIGHT_FIRE:
                this.fireState = FireState.STRONG_FIRE;
                break;
            case STRONG_FIRE:
                break;
            case DRY:
                this.fireState = FireState.LIGHT_FIRE;
                break;

        }
        if (this.fireState == FireState.STRONG_FIRE) {
            this.fireEngines.clear();
        }
    }

    @Override
    public FireState extinguish() throws GameException {
        switch (this.fireState) {
            case DRY:
            case LIGHT_FIRE:
                this.setFireState(FireState.WET);
                return FireState.WET;
            case STRONG_FIRE:
                this.setFireState(FireState.LIGHT_FIRE);
                return FireState.LIGHT_FIRE;
            case WET:
                throw new GameException(Errors.CANNOT_EXTINGUISH_WET_FOREST);
            case NON_BURNABLE_FIELD:
                throw new GameException(Errors.SOMETHING_WRONG);
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.fireState.getDisplayName());
        Collections.sort(fireEngines);
        for (FireEngine fe : fireEngines) {
            result.append("," + fe.getId());
        }
        return result.toString();
    }
}
