package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class models the forest field implementing the Field interface.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class ForestField implements Field {
    private final List<FireEngine> fireEngines;
    private final FireState initialState;
    private final FireEngine initialEngine;
    private FireState fireState;
    private boolean alreadyModified;

    /**
     * The constructor if no fireEngine is initially placed on the field.
     *
     * @param initialState the initial FireSate of the field.
     */
    public ForestField(FireState initialState) {
        this.fireEngines = new ArrayList<>();
        this.fireState = initialState;
        this.alreadyModified = false;
        this.initialState = initialState;
        this.initialEngine = null;
    }

    /**
     * The constructor there is a fireEngine placed on the field initially.
     *
     * @param fireEngine the fireEngine initially placed on the field.
     */
    public ForestField(FireEngine fireEngine) {
        this.fireState = FireState.DRY;
        this.initialState = FireState.DRY;
        this.fireEngines = new ArrayList<>();
        this.fireEngines.add(fireEngine);
        this.initialEngine = fireEngine;
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
    public void burn() {
        this.alreadyModified = true;
        switch (this.fireState) {
            case WET:
                this.fireState = FireState.DRY;
                break;
            case LIGHT_FIRE:
                this.fireState = FireState.STRONG_FIRE;
                break;
            case DRY:
                this.fireState = FireState.LIGHT_FIRE;
                break;
            default:
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
                this.fireState = FireState.WET;
                return FireState.WET;
            case STRONG_FIRE:
                this.fireState = FireState.LIGHT_FIRE;
                return FireState.LIGHT_FIRE;
            case WET:
                throw new GameException(Errors.CANNOT_EXTINGUISH_WET_FOREST);
            case NON_BURNABLE_FIELD:
                throw new GameException(Errors.SOMETHING_WRONG);
            default:
                break;
        }
        return null;
    }

    @Override
    public void reset() {
        this.fireState = this.initialState;
        this.fireEngines.clear();
        if (this.initialEngine != null) {
            initialEngine.reset();
            this.fireEngines.add(initialEngine);
        }

    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.fireState.getDisplayName());
        Collections.sort(fireEngines);
        for (FireEngine fe : fireEngines) {
            result.append(",").append(fe.getId());
        }
        return result.toString();
    }
}
